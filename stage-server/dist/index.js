"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
exports.__esModule = true;
var express_1 = __importDefault(require("express"));
var mongoose_1 = __importDefault(require("mongoose"));
var stage_model_1 = __importDefault(require("./stage.model"));
var body_parser_1 = __importDefault(require("body-parser"));
var app = (0, express_1["default"])();
app.use(body_parser_1["default"].json());
var uri = "mongodb://localhost:27017/carrer_up";
mongoose_1["default"].connect(uri, function (err) {
    if (err) {
        console.log(err);
    }
    else
        console.log("Mongo Data connected ");
});
app.post("/stages", function (req, res) {
    var stage = new stage_model_1["default"](req.body);
    stage.save(function (err) {
        if (err)
            res.status(500).send(err);
        else
            res.send(stage);
    });
});
app.put("/stages/:id", function (req, res) {
    var stage = stage_model_1["default"].findByIdAndUpdate(req.params.id, req.body, function (err) {
        if (err)
            res.status(500).send(err);
        else
            res.send("stage update");
    });
});
app.get("/stages", function (req, res) {
    // res.send("<h1> Test Express avec type script<\h1>")
    stage_model_1["default"].find(function (err, stages) {
        if (err) {
            res.status(500).send(err);
        }
        else {
            res.send(stages);
        }
    });
});
app.get("/stages/:id", function (req, res) {
    var stage = stage_model_1["default"].findById(req.params.id, function (err, stage) {
        if (err) {
            res.status(500).send(err);
        }
        else
            res.send(stage);
    });
});
app["delete"]("/stages/:id", function (req, res) {
    var stage = stage_model_1["default"].findByIdAndDelete(req.params.id, function (err, stage) {
        if (err) {
            res.status(500).send(err);
        }
        else
            res.send(stage);
    });
});
app.get('/', function (req, res) {
    var _a, _b;
    var societe = req.query.societe || '';
    var domaine = req.query.domaine || '';
    var sujet = req.query.sujet || '';
    var page = parseInt(((_a = req.query.page) === null || _a === void 0 ? void 0 : _a.toString()) || '1');
    var size = parseInt(((_b = req.query.size) === null || _b === void 0 ? void 0 : _b.toString()) || '5');
    stage_model_1["default"].paginate({ $and: [{ societe: { $regex: ".*(?i)" + societe + ".*" } }, { domaine: { $regex: ".*(?i)" + domaine + ".*" } }, { sujet: { $regex: ".*(?i)" + sujet + ".*" } }] }, { page: page, limit: size }, function (err, stages) {
        if (err)
            res.status(500).send(err);
        else
            res.send(stages);
    });
});
app.listen(8085, function () {
    console.log("server started ...");
});
