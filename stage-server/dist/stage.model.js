"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
exports.__esModule = true;
var mongoose_1 = __importDefault(require("mongoose"));
var mongoose_paginate_1 = __importDefault(require("mongoose-paginate"));
var Stage = new mongoose_1["default"].Schema({
    societe: { type: String, required: true },
    idRh: { type: String, required: true },
    sujet: { type: String, required: true },
    domaine: { type: Array, required: true },
    datedebut: { type: Date, required: true },
    dateFin: { type: Date, required: true },
    publishingdate: { type: Date, required: true, "default": new Date() },
    available: { type: Boolean, required: true, "default": true },
    description: { type: String, required: true }
});
Stage.plugin(mongoose_paginate_1["default"]);
var stage = mongoose_1["default"].model("Stage", Stage);
exports["default"] = stage;
