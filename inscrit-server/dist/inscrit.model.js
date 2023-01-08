"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
exports.__esModule = true;
var mongoose_1 = __importDefault(require("mongoose"));
var mongoose_paginate_1 = __importDefault(require("mongoose-paginate"));
//2)
var inscritSchema = new mongoose_1["default"].Schema({
    etat: {
        type: String,
        "enum": ['accepted', 'refused', 'In progress'],
        required: true,
        "default": 'In progress'
    },
    date: { type: Date, required: true, "default": new Date() },
    idSession: { type: Number, required: true },
    idUser: { type: Number, required: true }
});
//3)
inscritSchema.plugin(mongoose_paginate_1["default"]);
var inscrit = mongoose_1["default"].model("inscrit", inscritSchema);
exports["default"] = inscrit;
