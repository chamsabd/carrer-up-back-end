"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
exports.__esModule = true;
var express_1 = __importDefault(require("express"));
var mongoose_1 = __importDefault(require("mongoose"));
var stage_model_1 = __importDefault(require("./stage.model"));
var body_parser_1 = __importDefault(require("body-parser"));
var multer = require('multer');
var path = require('path');
var PORT = 3000;
var eurekaHelper = require('./eureka-helper');
eurekaHelper.registerWithEureka("stage-server", PORT);
var app = (0, express_1["default"])();
app.use(body_parser_1["default"].json());
app.use(body_parser_1["default"].urlencoded({ extended: true }));
var store = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, './uploads');
    },
    filename: function (req, file, cb) {
        cb(null, Date.now() + '.' + file.originalname);
    }
});
var upload = multer({ storage: store }).single('file');
var uri = "mongodb://localhost:27017/carrer_up";
mongoose_1["default"].connect(uri, function (err) {
    if (err) {
        console.log(err);
    }
    else
        console.log("Mongo Data connected ");
});
app.post('/file/upload/:id', function (req, res, next) {
    console.log(req.params["id"]);
    var id = req.params["id"];
    res.setHeader("Access-Control-Allow-Origin", "*");
    upload(req, res, function (err) {
        if (err) {
            return res.status(501).json({ error: err });
        }
        var filepath = path.join(__dirname, '../uploads') + '/' + req.file.filename;
        var stage = stage_model_1["default"].findByIdAndUpdate(req.params["id"], {
            $push: { "cv": { "path": filepath, "name": req.file.originalname, "size": req.file.size, "registername": req.file.filename } }
        }, { "new": true }, function (err) {
            if (err)
                res.status(500).send(err);
        });
        console.log(stage);
        //do all database record saving activity
        res.setHeader("Access-Control-Allow-Origin", "*");
        return res.json({ originalname: req.file.originalname, uploadname: req.file.filename });
    });
});
app.post('/file/download', function (req, res, next) {
    res.setHeader("Access-Control-Allow-Origin", "*");
    var filepath = path.join(__dirname, '../uploads') + '/' + req.body.filename;
    //  res.sendFile(filepath);
    var o = req.body.filename;
    console.log(o);
    var arr = o.substring(o.indexOf('.') + 1);
    console.log(arr);
    res.download(filepath, arr, null);
});
app.post("/stages", function (req, res) {
    console.log("stage " + req.body.societe);
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
            res.send(req.body);
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
app.listen(PORT, function () {
    console.log("server started ...");
});
