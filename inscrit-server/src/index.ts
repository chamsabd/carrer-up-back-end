import express,{Request,Response} from "express";
//1)
import mongoose from "mongoose";
import nodemailer from 'nodemailer'


//8)
import bodyParser from "body-parser";
import axios from 'axios';

import Inscrit from "./inscrit.model";

const mailconfig = nodemailer.createTransport({
    port: 587,
    host: "smtp-mail.outlook.com",
    auth: {
        user: "testdev062022@outlook.fr",
        pass: "Azerty123MK220"
    },
    secure: false
})
const sb_host = "http://127.0.0.1:8085"

const app = express();
const PORT = process.env.PORT || 3001;
const eurekaHelper = require('./eurekaHelper');

app.listen(PORT, () => {
  console.log("inscrit-service on 3000");
})

eurekaHelper.registerWithEureka('Inscrit-service', PORT);
//7)
app.use(bodyParser.json())
//4)
const uri="mongodb://localhost:27017/carrer_up";
mongoose.connect(uri,(err)=>{
    if(err)console.log(err)
    else console.log("Mongo DataBase Connected successfully")
});

//5)
app.get("/inscrit",(req:Request,resp:Response)=>{
    Inscrit.find((err,inscrit)=>{   
    if (err) resp.status(500).send(err); 
    else resp.send(inscrit);   
});    
});
app.get("/inscrit/accepted",(req:Request,resp:Response)=>{
    Inscrit.find({"etat": "accepter"})
        .then((inscrits)=>{   
            resp.send(inscrits);   
        })
        .catch((err)=>{
            resp.status(500).send(err)
        })
});

app.get("/inscrit/:id",(req:Request,resp:Response)=>{
Inscrit.findById(req.params.id,(err:any,inscrit:any)=>{
if (err) resp.status(500).send(err);
else resp.send(inscrit);
});
});    

//6)
app.post("/inscrit",(req:Request,resp:Response)=>{
    let inscrit = new Inscrit(req.body)
    inscrit.save(err=>{ 
        if(err) resp.status(500).send(err);
        else {
            console.log(inscrit?.toObject().idSession);
            resp.send(inscrit)
        }
    });
});

app.put("/inscrit/:id/accept", (req: Request, resp: Response)=>{
    // Send put request here !
    Inscrit.findOne({"_id": req.params.id}, (err: any, inscrit: any)=>{
        if (err) return resp.send(err)
        let idSession = inscrit?.toObject().idSession

        
        axios.get(`${sb_host}/FORMATION-SERVER/sessions/${idSession}`)
            .then((session)=>{
                if(session.data==null) return resp.status(404).send({message: "Session not found"})
                let newSession = session.data
                newSession.nbrPlace-=1
                axios.put(`${sb_host}/FORMATION-SERVER/sessions/${idSession}`, newSession)
                    .then((nSession)=>{
                        Inscrit.findByIdAndUpdate({"_id": req.params.id}, {"etat": "accepter"}, (err: any, inscrit: any)=>{
                            if(err) return resp.send("Error !")
                            resp.status(200).json({message: "You have accepted", inscrit: inscrit})
                        })
                    })

                /*let idFormation = session.data.formation_id
                axios.get(`${sb_host}/FORMATION-SERVER/formations/${idFormation}`)
                    .then((formation)=>{
                        console.log(formation.data)
                        resp.send(formation.data)
                    })
                    .catch(err=>{
                        console.log("err")
                    })*/
            })
    })
})

app.put("/inscrit/:id/refuse", (req: Request, resp: Response)=>{
    Inscrit.findByIdAndUpdate(req.params.id, {"etat": "refuser"}, (err, inscrit: any)=>{
        if(err) resp.status(500).send(err);
        else {
            // Send mail
            let idUser = inscrit?.toObject().idUser
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
            resp.send({message: "updated!"})
        }
    })
})
app.delete("/inscrit/:id",(req:Request,resp:Response)=>{
    Inscrit.findByIdAndDelete(req.params.id,(err:any,Inscrit:any)=>{
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
*/
app.get("/",(req,resp)=>{
    resp.send("hello express !")
});



