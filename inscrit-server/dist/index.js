"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
exports.__esModule = true;
var express_1 = __importDefault(require("express"));
//1)
var mongoose_1 = __importDefault(require("mongoose"));
var nodemailer_1 = __importDefault(require("nodemailer"));
//8)
var body_parser_1 = __importDefault(require("body-parser"));
var axios_1 = __importDefault(require("axios"));
var inscrit_model_1 = __importDefault(require("./inscrit.model"));
var mailconfig = nodemailer_1["default"].createTransport({
    port: 587,
    host: "smtp-mail.outlook.com",
    auth: {
        user: "testdev062022@outlook.fr",
        pass: "Azerty123MK220"
    },
    secure: false
});
var sb_host = "http://127.0.0.1:8085";
var app = (0, express_1["default"])();
var PORT = process.env.PORT || 3001;
var eurekaHelper = require('./eurekaHelper');
app.listen(PORT, function () {
    console.log("inscrit-service on 3000");
});
eurekaHelper.registerWithEureka('Inscrit-service', PORT);
//7)
app.use(body_parser_1["default"].json());
//4)
var uri = "mongodb://localhost:27017/carrer_up2";
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
//6)
app.post("/inscrit", function (req, resp) {
    var inscrit = new inscrit_model_1["default"](req.body);
    inscrit.save(function (err) {
        if (err)
            resp.status(500).send(err);
        else {
            console.log(inscrit === null || inscrit === void 0 ? void 0 : inscrit.toObject().idSession);
            resp.send(inscrit);
        }
    });
});
app.put("/inscrit/:id/accept", function (req, resp) {
    // Send put request here ! tw hajtk données eli f lbase tw !!? hahaha!! mlaa bech wohh
    inscrit_model_1["default"].findOne({ "_id": req.params.id }, function (err, inscrit) {
        if (err)
            return resp.send(err);
        var idSession = inscrit === null || inscrit === void 0 ? void 0 : inscrit.toObject().idSession;
        console.log(idSession);
        // heya ta3ml fel formation fonction ta3ml -1 l nbrPlace par id Session
        axios_1["default"].get("".concat(sb_host, "/SESSION-SERVER/").concat(idSession, "/sessions"))
            .then(function (session) {
            var idFormation = session.data.Sessions[0].idFormation;
            axios_1["default"].get("".concat(sb_host, "/formations/").concat(idFormation))
                .then(function (formation) {
                //console.log(formation.data)
                resp.send(formation);
            })["catch"](function (err) {
                console.log("err");
                console.log(err);
            });
        });
    });
});
app.put("/inscrit/:id/refuse", function (req, resp) {
    inscrit_model_1["default"].findByIdAndUpdate(req.params.id, { "etat": "refuser" }, function (err, inscrit) {
        if (err)
            resp.status(500).send(err);
        else {
            // Send mail
            var idUser = inscrit === null || inscrit === void 0 ? void 0 : inscrit.toObject().idUser;
            /*axios.get(`${sb_host}/USER-SERVER/users/${idUser}`)
                .then((user)=>{
                    let userEmail = user.data.Users[0].email
                    const mailData = {
                        from: 'From..',
                        to: userEmail,
                        subject: 'Votre demande a été refusée',
                        text: "Votre demande a été refusée",
                    }
                    mailconfig.sendMail(mailData, (err, info)=>{
                        if(err) console.log(err)
                        if(info) console.log(info)
                    })
                    resp.send({message: "updated!"})
                })*/
            var mailData = {
                from: 'testdev062022@outlook.fr',
                to: "safedhaouadi@bizerte.r-iset.tn",
                subject: 'A propos votre demande',
                text: "Votre demande a été refusée"
            };
            mailconfig.sendMail(mailData, function (err, info) {
                if (err)
                    console.log(err);
                if (info)
                    console.log(info);
            });
            resp.send({ message: "updated!" });
        }
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
/*
app.get('/inscritParPage',(req:Request,res:Response)=>{
    const page:number = parseInt(req.query.page?.toString()||'1');
    const size:number = parseInt(req.query.size?.toString()||'5');
    Inscrit.paginate({},{page:page,limit:size},(err:any,inscrit:any)=>{
        if(err) res.status(500).send(err);
        else res.send(inscrit);
    });
})
*/
app.get("/", function (req, resp) {
    resp.send("hello express !");
});
