"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
exports.__esModule = true;
var express_1 = __importDefault(require("express"));
var mongoose_1 = __importDefault(require("mongoose"));
var stage_model_1 = __importDefault(require("./stage.model"));
var body_parser_1 = __importDefault(require("body-parser"));
var jwt = require('jsonwebtoken');
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
    validateToken(req, res);
    console.log(req.params["id"]);
    var id = req.params["id"];
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
        return res.json({ originalname: req.file.originalname, uploadname: req.file.filename });
    });
});
var username = "";
app.post('/file/download', function (req, res, next) {
    validateToken(req, res);
    var filepath = path.join(__dirname, '../uploads') + '/' + req.body.filename;
    //  res.sendFile(filepath);
    var o = req.body.filename;
    var arr = o.substring(o.indexOf('.') + 1);
    res.download(filepath, arr, null);
});
var societe = "";
var role = "";
function validateToken(req, res) {
    var JWT_HEADER_NAME = "Authorization";
    var SECRET = "chams-carrer-up@gmail.tn";
    var HEADER_PREFIX = "Bearer ";
    var tokenHeaderKey = JWT_HEADER_NAME;
    var jwtSecretKey = SECRET;
    try {
        var tokenb = req.header(tokenHeaderKey);
        var token = tokenb;
        //return res.status(200).send(token);
        if (tokenb.startsWith('Bearer ')) {
            // Remove Bearer from string
            token = tokenb.substring(HEADER_PREFIX.length);
        }
        var verified = jwt.verify(token, jwtSecretKey);
        if (verified) {
            var decode = jwt.decode(token, jwtSecretKey);
            console.log(decode);
            username = decode.sub;
            societe = decode.societe;
            role = decode.roles;
            console.log(societe);
            if (decode.roles != "ROLE_RH") {
                var req_url = req.baseUrl + req.route.path;
                if ((req.method == "POST" || req.method == "PUT")) {
                    res.status(401).send("Unauthorized!");
                }
                else if (req_url.includes("/file/download")) {
                    res.status(401).send("Unauthorized!");
                }
            }
            else if (decode.roles != "ROLE_USER") {
                var req_url = req.baseUrl + req.route.path;
                if (req_url.includes("/file/upload/:id")) {
                    res.status(401).send("Unauthorized!");
                }
            }
        }
        else {
            // Access Denied
            res.status(401).send("Unauthorized!");
        }
    }
    catch (error) {
        // Access Denied
        res.status(401).send(error);
    }
}
app.post("/stages", function (req, res) {
    validateToken(req, res);
    req.body.societe = societe;
    // console.log("stage "+req.body.societe);
    stage_model_1["default"].aggregate([
        { "$match": { "sujet": req.body.sujet } },
    ], function (err, data) {
        if (err)
            throw err;
        if (data.length == 0) {
            var stage_1 = new stage_model_1["default"](req.body);
            stage_1.save(function (err) {
                if (err)
                    res.status(500).send(err);
                else
                    res.send(stage_1);
            });
        }
        else
            res.status(500).send("subject alradey used");
    });
});
app.put("/stages/:id", function (req, res) {
    validateToken(req, res);
    req.body.societe = societe;
    var stage = stage_model_1["default"].findByIdAndUpdate(req.params.id, req.body, function (err) {
        if (err)
            res.status(500).send(err);
        else
            res.send(req.body);
    });
});
app.get("/stages", function (req, res) {
    // res.send("<h1> Test Express avec type script<\h1>")
    validateToken(req, res);
    if (role == "ROLE_RH") {
        stage_model_1["default"].aggregate([
            { "$match": { "societe": societe } },
        ], function (err, data) {
            if (err)
                throw err;
            res.send(data);
        });
    }
    else {
        stage_model_1["default"].find(function (err, stages) {
            if (err) {
                res.status(500).send(err);
            }
            else {
                res.send(stages);
            }
        });
    }
});
app.get("/stages/:id", function (req, res) {
    validateToken(req, res);
    var stage = stage_model_1["default"].findById(req.params.id, function (err, stage) {
        if (err) {
            res.status(500).send(err);
        }
        else
            res.send(stage);
    });
});
app["delete"]("/stages/:id", function (req, res) {
    validateToken(req, res);
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
