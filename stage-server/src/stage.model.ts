import mongoose from "mongoose";
import mongoosePaginate from "mongoose-paginate";
let Stage=new mongoose.Schema({
    societe:{type:String,required:true},
    idRh:{type:String,required:true},
    sujet:{type:String,required:true},
    domaine:{type:Array,required:true},
    datedebut:{type:Date,required:true},
    dateFin:{type:Date,required:true},
    publishingdate:{type:Date,required:true,default:new Date()},
    available:{type:Boolean,required:true,default:true},
description:{type:String,required:true}  
})
Stage.plugin(mongoosePaginate)
const stage=mongoose.model("Stage",Stage)
export default stage;