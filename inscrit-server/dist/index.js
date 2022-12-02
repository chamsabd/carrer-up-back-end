"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
exports.__esModule = true;
var express_1 = __importDefault(require("express"));
//1)
var mongoose_1 = __importDefault(require("mongoose"));
var inscrit_model_1 = __importDefault(require("./inscrit.model"));
//8)
var body_parser_1 = __importDefault(require("body-parser"));
var app = (0, express_1["default"])();
var PORT = process.env.PORT || 3000;
//const eurekaHelper = require('./Inscrit');
app.listen(PORT, function () {
    console.log("inscrit-service on 3000");
});
//eurekaHelper.registerWithEureka('Inscrit-service', PORT);
//7)
app.use(body_parser_1["default"].json());
//4)
var uri = "mongodb://localhost:27017/carrer_up";
mongoose_1["default"].connect(uri, function (err) {
    if (err)
        console.log(err);
    else
        console.log("Mongo DataBase Connected successfully");
});
//5)
app.get("/inscrit", function (req, resp) {
    inscrit_model_1["default"].find(function (err, inscrit) {
        if (err)
            resp.status(500).send(err);
        else
            resp.send(inscrit);
    });
});
app.get("/inscrit/:id", function (req, resp) {
    inscrit_model_1["default"].findById(req.params.id, function (err, inscrit) {
        if (err)
            resp.status(500).send(err);
        else
            resp.send(inscrit);
    });
});
app.put("/inscrit/:id", function (req, resp) {
    inscrit_model_1["default"].findByIdAndUpdate(req.params.id, req.body, function (err) {
        if (err)
            resp.status(500).send(err);
        else
            resp.send("Inscrit updated succeslully");
    });
});
//6)
app.post("/inscrit", function (req, resp) {
    var inscrit = new inscrit_model_1["default"](req.body);
    inscrit.save(function (err) {
        if (err)
            resp.status(500).send(err);
        else
            resp.send(inscrit);
    });
});
app["delete"]("/inscrit/:id", function (req, resp) {
    inscrit_model_1["default"].findByIdAndDelete(req.params.id, function (err, Inscrit) {
        if (err)
            resp.status(500).send(err);
        else
            resp.send("inscrit deleted");
    });
});
app.get('/inscritParPage', function (req, res) {
    var _a, _b;
    var page = parseInt(((_a = req.query.page) === null || _a === void 0 ? void 0 : _a.toString()) || '1');
    var size = parseInt(((_b = req.query.size) === null || _b === void 0 ? void 0 : _b.toString()) || '5');
    inscrit_model_1["default"].paginate({}, { page: page, limit: size }, function (err, inscrit) {
        if (err)
            res.status(500).send(err);
        else
            res.send(inscrit);
    });
});
app.get("/", function (req, resp) {
    resp.send("hello express !");
});
