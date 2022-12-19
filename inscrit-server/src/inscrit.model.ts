import mongoose from "mongoose";
import mongoosePaginate from "mongoose-paginate";
//2)
let inscritSchema=new mongoose.Schema({
    etat:{
        type: String,
        enum: ['accepter', 'refuser','en cours de traitement'],
        required:true,
        default:'en cours de traitement'
    },
    date:{type:Date,required:true,default:new Date()},
    idSession:{type:Number,required:true},
    idUser:{type:Number,required:true},
});

//3)
inscritSchema.plugin(mongoosePaginate)
const inscrit=mongoose.model("inscrit",inscritSchema)
export default inscrit;