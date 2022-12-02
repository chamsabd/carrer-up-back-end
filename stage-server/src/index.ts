import  express,{Request,response,Response}  from "express";
import mongoose from "mongoose";
import Stage from "./stage.model";
import bodyParser from "body-parser"
const app = express();
app.use(bodyParser.json())
const uri="mongodb://localhost:27017/carrer_up";
mongoose.connect(uri,(err)=>{
    if (err) {
        console.log(err);
        
    }
    else console.log("Mongo Data connected ");
    
})
app.post("/stages",(req:Request,res:Response)=>{
    let stage=new Stage(req.body)
    stage.save((err)=>{
        if (err) res.status(500).send(err)
            
         else  res.send(stage)
    })
})
app.put("/stages/:id",(req:Request,res:Response)=>{
    let stage=Stage.findByIdAndUpdate(req.params.id,req.body,(err:any)=>{
        if (err) res.status(500).send(err)
            
         else  res.send("stage update")
    });

   
})
app.get("/stages",(req:Request,res:Response)=>{
   // res.send("<h1> Test Express avec type script<\h1>")
   Stage.find((err,stages)=>{
if (err) {
    res.status(500).send(err)
    
} else {
    res.send(stages)
}
   })
});
app.get("/stages/:id",(req,res)=>{
    let stage=Stage.findById(req.params.id,(err:any,stage:any)=>{
        if (err) {res.status(500).send(err)
            
        } else  res.send(stage)
    }
    );
    });
    app.delete("/stages/:id",(req,res)=>{
        let stage=Stage.findByIdAndDelete(req.params.id,(err:any,stage:any)=>{
            if (err) {res.status(500).send(err)
                
            } else  res.send(stage)
        }
        );
        });
   
        app.get('/',(req:Request,res:Response)=>{
            const societe = req.query.societe || '';
            const domaine = req.query.domaine || '';
            const sujet = req.query.sujet || '';
        
            const page:number = parseInt(req.query.page?.toString()||'1');
        
            const size:number = parseInt(req.query.size?.toString()||'5');

            Stage.paginate({$and:[{societe:{$regex:".*(?i)"+societe+".*"}},{domaine:{$regex:".*(?i)"+domaine+".*"}},{sujet:{$regex:".*(?i)"+sujet+".*"}}]},{page:page,limit:size},(err:any,stages:any)=>{
        
                if(err) 
                res.status(500).send(err);
        
                else res.send(stages);
        
            });
        
            
        
        });
    
   

app.listen(8085,()=>{
    console.log("server started ...");
    
})
