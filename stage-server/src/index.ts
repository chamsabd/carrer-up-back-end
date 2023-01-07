import  express,{Request,response,Response}  from "express";
import mongoose from "mongoose";
import Stage from "./stage.model";
import bodyParser from "body-parser"
import { validateHeaderName } from "http";
const jwt = require('jsonwebtoken');
var multer = require('multer');
var path = require('path');
const PORT = 3000;
const eurekaHelper = require('./eureka-helper');
eurekaHelper.registerWithEureka("stage-server",PORT);

const app = express();
app.use(bodyParser.json())

app.use(bodyParser.urlencoded({extended: true}))

var store = multer.diskStorage({
    destination:function(req:any,file:any,cb:any){
        cb(null, './uploads');
    },
    filename:function(req:any,file:any,cb:any){
        cb(null, Date.now()+'.'+file.originalname);
    }
});


var upload = multer({storage:store}).single('file');


const uri="mongodb://localhost:27017/carrer_up";
mongoose.connect(uri,(err)=>{
    if (err) {
        console.log(err); 
    }
    else console.log("Mongo Data connected ");
    
})
app.post('/file/upload/:id', function(req:any,res:any,next){
    validateToken(req,res);
    console.log(req.params["id"]);
    
    var id=req.params["id"];
    
    res.setHeader("Access-Control-Allow-Origin","*")
    upload(req,res,function(err:any){
        if(err){
            return res.status(501).json({error:err});
        }
        var filepath = path.join(__dirname,'../uploads') +'/'+ req.file.filename;
        let stage= Stage.findByIdAndUpdate(
            req.params["id"],
            {
        
                $push: {"cv":{"path":filepath,"name":req.file.originalname,"size":req.file.size,"registername":req.file.filename}},
                
            }, {new:true} ,(err:any)=>{
                if (err) res.status(500).send(err)
                    
                
            });
console.log(stage);

        
        
        //do all database record saving activity
        res.setHeader("Access-Control-Allow-Origin","*")
        return res.json({originalname:req.file.originalname, uploadname:req.file.filename});
    });
});

app.post('/file/download', function(req,res,next){
    validateToken(req,res);
    res.setHeader("Access-Control-Allow-Origin","*")
   var filepath = path.join(__dirname,'../uploads') +'/'+ req.body.filename;
  //  res.sendFile(filepath);
    var o=req.body.filename
   
    
   var  arr = o.substring(o.indexOf('.')+1);
  
 res.download(filepath,arr,null);
});


  function  validateToken(req:any, res:any){
    var JWT_HEADER_NAME="Authorization";
	 var SECRET="chams-carrer-up@gmail.tn"; 

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
      
       if( decode.roles !="ROLE_RH" ){
        let req_url = req.baseUrl+req.route.path;
      
        if(req_url.includes("stages/:id") && (req.method=="POST" || req.method=="PUT") ){
            return res.status(401).send("Unauthorized!");
        }
        else if(req_url.includes("/file/download")){
            return res.status(401).send("Unauthorized!");
        }
    }
   else if (decode.roles !="ROLE_USER") {
    let req_url = req.baseUrl+req.route.path;
    if(req_url.includes("/file/upload/:id")){
        return res.status(401).send("Unauthorized!");
    }
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



app.post("/stages",(req:Request,res:Response)=>{
    validateToken(req,res);
    console.log("stage "+req.body.societe);
    let stage=new Stage(req.body)
    stage.save((err)=>{
        if (err) res.status(500).send(err)
            
         else  res.send(stage)
    })
})
app.put("/stages/:id",(req:Request,res:Response)=>{
    validateToken(req,res);
    let stage=Stage.findByIdAndUpdate(req.params.id,req.body,(err:any)=>{
        if (err) res.status(500).send(err)
            
         else  res.send(req.body)
    });

   
})
app.get("/stages",(req:Request,res:Response)=>{
   // res.send("<h1> Test Express avec type script<\h1>")
   validateToken(req,res);
   Stage.find((err,stages)=>{
if (err) {
    res.status(500).send(err)
    
} else {
    res.send(stages)
}
   })
});
app.get("/stages/:id",(req,res)=>{
    validateToken(req,res);
    let stage=Stage.findById(req.params.id,(err:any,stage:any)=>{
        if (err) {res.status(500).send(err)
            
        } else  res.send(stage)
    }
    );
    });
    app.delete("/stages/:id",(req,res)=>{
        validateToken(req,res);
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
    
   

app.listen(PORT,()=>{
    console.log("server started ...");
    
})
