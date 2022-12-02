import express,{Request,Response} from "express";
//1)
import mongoose from "mongoose";
import Inscrit from "./inscrit.model";
//8)
import bodyParser from "body-parser";
const app = express();
const PORT = process.env.PORT || 3000;
//const eurekaHelper = require('./Inscrit');

app.listen(PORT, () => {
  console.log("inscrit-service on 3000");
})

//eurekaHelper.registerWithEureka('Inscrit-service', PORT);
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

app.get("/inscrit/:id",(req:Request,resp:Response)=>{
Inscrit.findById(req.params.id,(err:any,inscrit:any)=>{
if (err) resp.status(500).send(err);
else resp.send(inscrit);
});
});    

app.put("/inscrit/:id",(req:Request,resp:Response)=>{
    Inscrit.findByIdAndUpdate(req.params.id,req.body,(err:any)=>{
        if(err) resp.status(500).send(err);
        else resp.send("Inscrit updated succeslully");
    })

});

//6)
app.post("/inscrit",(req:Request,resp:Response)=>{
    let inscrit = new Inscrit(req.body)
    inscrit.save(err=>{ 
        if(err) resp.status(500).send(err);
        else resp.send(inscrit)
    });
});

app.delete("/inscrit/:id",(req:Request,resp:Response)=>{
    Inscrit.findByIdAndDelete(req.params.id,(err:any,Inscrit:any)=>{
    if (err) resp.status(500).send(err);
        else resp.send("inscrit deleted");
    });
});

app.get('/inscritParPage',(req:Request,res:Response)=>{
    const page:number = parseInt(req.query.page?.toString()||'1');
    const size:number = parseInt(req.query.size?.toString()||'5');
    Inscrit.paginate({},{page:page,limit:size},(err:any,inscrit:any)=>{
        if(err) res.status(500).send(err);
        else res.send(inscrit);
    });
})

app.get("/",(req,resp)=>{
    resp.send("hello express !")
});



