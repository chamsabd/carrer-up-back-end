import mongoose from "mongoose";
import mongoosePaginate from "mongoose-paginate";
let Stage=new mongoose.Schema({
    societe:{type:String,required:true},
    sujet:{type:String,required:true},
    domaine:{type:String,required:false},
    datedebut:{type:Date,required:false},
    dateFin:{type:Date,required:false},
    publishingdate:{type:Date,required:true,default:new Date()},
    available:{type:Boolean,required:true,default:true},
description:{type:String,required:true} ,
cv:{type:Array,required:false} 
})




Stage.plugin(mongoosePaginate)
const stage=mongoose.model("Stage",Stage)
export default stage;