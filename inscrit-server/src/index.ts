import express, { Request, Response } from "express";
//1)
import mongoose from "mongoose";
import cors from 'cors'
//import nodemailer from 'nodemailer'

const jwt = require('jsonwebtoken');
//8)
import bodyParser from "body-parser";
import axios from 'axios';

import Inscrit from "./inscrit.model";

/*const mailconfig = nodemailer.createTransport({
    port: 587,
    host: "smtp-mail.outlook.com",
    auth: {
        user: "testdev062022@outlook.fr",
        pass: "Azerty123MK220"
    },
    secure: false
})*/
const sb_host = "http://127.0.0.1:8085"

const app = express();
const PORT = process.env.PORT || 3001;
const eurekaHelper = require('./eurekaHelper');

app.listen(PORT, () => {
    console.log("inscrit-service on 3000");
})
function  validateToken(req:any, res:any){
    var JWT_HEADER_NAME="Authorization";
	 var SECRET="chams-carrer-up@gmail.tn"; 
 var EXPIRATION=10*24*3600; 
	 var HEADER_PREFIX="Bearer "; 
    let tokenHeaderKey = JWT_HEADER_NAME;
    let jwtSecretKey = SECRET;
  
    try {
        const tokenb = req.header(tokenHeaderKey);
        var  token=tokenb;
        //return res.status(200).send(token);
        if (tokenb.startsWith('Bearer ')) {
            // Remove Bearer from string
               token=tokenb!.substring(HEADER_PREFIX.length)
        }
    
        const verified = jwt.verify(token, jwtSecretKey);
        if(verified){
       var    decode= jwt.decode(token, jwtSecretKey)
       console.log(decode);
       if( decode.roles =="ROLE_RESPONSABLE" ){
        let req_url = req.baseUrl+req.route.path;
        if(req_url.includes("/inscrit") || req.method=="POST" ){
            return res.status(401).send("Unauthorized!");
        }
    }else{
        return res.status(401).send("Unauthorized!");
    }
        }else{
            // Access Denied
            return res.status(401).send("non");
        }
    } catch (error) {
        // Access Denied
        return res.status(401).send(error);
    }
     }


eurekaHelper.registerWithEureka('Inscrit-service', PORT);
//7)
app.use(cors())
app.use(bodyParser.json())
//4)
const uri = "mongodb://localhost:27017/carrer_up";
mongoose.connect(uri, (err) => {
    if (err) console.log(err)
    else console.log("Mongo DataBase Connected successfully")
});

//5)
app.get("/inscrit", (req: Request, resp: Response) => {
    Inscrit.find((err, inscrit) => {
        if (err) resp.status(500).send(err);
        else {
            resp.send(inscrit)
        };
    });
});
app.get("/inscrit/accepted", (req: Request, resp: Response) => {
    Inscrit.find({ "etat": "accepted" })
        .then((inscrits) => {
            resp.send(inscrits);
        })
        .catch((err) => {
            resp.status(500).send(err)
        })
});

app.get("/inscrit/:id", (req: Request, resp: Response) => {
    Inscrit.findById(req.params.id, (err: any, inscrit: any) => {
        if (err) resp.status(500).send(err);
        else resp.send(inscrit);
    });
});

//6)
app.post("/inscrit", (req: Request, resp: Response) => {
    let inscrit = new Inscrit(req.body)
    inscrit.save(err => {
        if (err) resp.status(500).send(err);
        else {
            console.log(inscrit?.toObject().idSession);
            resp.send(inscrit)
        }
    });
});

app.put("/inscrit/accept", (req: Request, resp: Response) => {
    let idInscrip = req.body.id
    // Send put request here !
    Inscrit.findOne({ "_id": idInscrip }, (err: any, inscrit: any) => {
        if (err) return resp.send("err")
        let idSession = inscrit?.toObject().idSession
        axios.get(`${sb_host}/formation-server/sessions/${idSession}`)
            .then((session) => {
                if (session.data == null) return resp.status(404).send({ message: "Session not found" })
                if (session.data.nbrPlace > 0) {
                    let newSession = session.data
                    newSession.nbrPlace -= 1

                    axios.put(`${sb_host}/formation-server/sessions/${idSession}`, newSession)
                        .then((nSession) => {
                            Inscrit.findByIdAndUpdate({ "_id": idInscrip }, { "etat": "accepted" }, (err: any, inscrit: any) => {
                                if (err) return resp.send("Error !")
                                // Send mail
                                let idUser = inscrit?.toObject().idUser
                                let mailData = {
                                    to: 'safadhaouadi319@gmail.com',
                                    subject: 'Votre demande a été accepter',
                                    text: "Votre demande a été accepter",
                                }
                                axios.post(`${sb_host}/EMAIL-SERVER/send`, mailData)
                                    .then((res_email) => {
                                        inscrit.etat = "accepted"
                                        resp.status(200).send({ message: "accepted" })
                                    }).catch((err) => {
                                        console.log("error")
                                        resp.status(500).send({ message: "mail error" })
                                    })
                            })
                        })
                        .catch((error) => {
                            console.log("error")
                            resp.status(500).json({ message: "Error" })
                        });
                } else {
                    return resp.status(404).send({ message: "place not found" })
                }
            })
    })
})
/*let idFormation = session.data.formation_id
             axios.get(`${sb_host}/formation-server/formations/${idFormation}`)
                 .then((formation)=>{
                     console.log(formation.data)
                     resp.send(formation.data)
                 })
                 .catch(err=>{
                     console.log("err")
                 })*/
app.put("/inscrit/refuse", (req: Request, resp: Response) => {
    let idInscrip = req.body.id
    Inscrit.findByIdAndUpdate(idInscrip, { "etat": "refused" }, (err, inscrit: any) => {
        if (err) resp.status(500).send(err);
        else {
            // Send mail
            let idUser = inscrit?.toObject().idUser
            let mailData = {
                to: 'safadhaouadi319@gmail.com',
                subject: 'Votre demande a été refusée',
                text: "Votre demande a été refusée",
            }
            axios.post(`${sb_host}/EMAIL-SERVER/send`, mailData)
                .then((res_email) => {
                    inscrit.etat = "refused"
                    resp.status(200).send({ message: "refused" })
                }).catch((err) => {
                    console.log("error")
                    resp.status(500).send({ message: "mail error" })
                })
        }
    })
})
/*axios.get(`${sb_host}/USER-SERVER/users/${idUser}`)
                .then((user)=>{
                    let userEmail = user.data.Users[0].email
                    const mailData = {
                        from: 'From..',
                        to: userEmail,
                        subject: 'Votre demande a été refusée',
                        text: "Votre demande a été refusée",
                    }
                    mailconfig.sendMail(mailData, (err, info)=>{
                        if(err) console.log(err)
                        if(info) console.log(info)
                    })
                    resp.send({message: "updated!"})
                })*/
app.delete("/inscrit/:id", (req: Request, resp: Response) => {
    Inscrit.findByIdAndDelete(req.params.id, (err: any, Inscrit: any) => {
        if (err) resp.status(500).send(err);
        else resp.send("inscrit deleted");
    });
});
/*
app.get('/inscritParPage',(req:Request,res:Response)=>{
    const page:number = parseInt(req.query.page?.toString()||'1');
    const size:number = parseInt(req.query.size?.toString()||'5');
    Inscrit.paginate({},{page:page,limit:size},(err:any,inscrit:any)=>{
        if(err) res.status(500).send(err);
        else res.send(inscrit);
    });
})



app.get('/inscritSearch',(req:Request,res:Response)=>{
    const search = req.query.search || '';
    const page:number = parseInt(req.query.page?.toString()||'1');
    const size:number = parseInt(req.query.size?.toString()||'5');

    Inscrit.paginate({etat:{$regex:".*(?i)"+search+".*"}},{page:page,limit:size},(err:any,inscrit:any)=>{
        if(err) res.status(500).send(err);
        else res.send(inscrit);
    });
    
});
*/
app.get("/", (req, resp) => {
    resp.send("hello express !")
});



