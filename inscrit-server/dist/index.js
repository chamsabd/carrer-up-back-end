"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
exports.__esModule = true;
var express_1 = __importDefault(require("express"));
//1)
var mongoose_1 = __importDefault(require("mongoose"));
var cors_1 = __importDefault(require("cors"));
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
app.use((0, cors_1["default"])());
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
        else {
            resp.send(inscrit);
        }
        ;
    });
});
app.get("/inscrit/accepted", function (req, resp) {
    inscrit_model_1["default"].find({ "etat": "accepted" })
        .then(function (inscrits) {
        resp.send(inscrits);
    })["catch"](function (err) {
        resp.status(500).send(err);
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
app.put("/inscrit/accept", function (req, resp) {
    var idInscrip = req.body.id;
    // Send put request here !
    inscrit_model_1["default"].findOne({ "_id": idInscrip }, function (err, inscrit) {
        if (err)
            return resp.send("err");
        var idSession = inscrit === null || inscrit === void 0 ? void 0 : inscrit.toObject().idSession;
        axios_1["default"].get("".concat(sb_host, "/FORMATION-SERVER/sessions/").concat(idSession))
            .then(function (session) {
            if (session.data == null)
                return resp.status(404).send({ message: "Session not found" });
            var newSession = session.data;
            newSession.nbrPlace -= 1;
            axios_1["default"].put("".concat(sb_host, "/FORMATION-SERVER/sessions/").concat(idSession), newSession)
                .then(function (nSession) {
                inscrit_model_1["default"].findByIdAndUpdate({ "_id": idInscrip }, { "etat": "accepted" }, function (err, inscrit) {
                    if (err)
                        return resp.send("Error !");
                    inscrit.etat = "accepted";
                    resp.status(200).json(inscrit);
                });
            })["catch"](function (error) {
                console.log("error");
                resp.status(500).json({ message: "Error" });
            });
            /*let idFormation = session.data.formation_id
            axios.get(`${sb_host}/FORMATION-SERVER/formations/${idFormation}`)
                .then((formation)=>{
                    console.log(formation.data)
                    resp.send(formation.data)
                })
                .catch(err=>{
                    console.log("err")
                })*/
        });
    });
});
app.put("/inscrit/refuse", function (req, resp) {
    var idInscrip = req.body.id;
    inscrit_model_1["default"].findByIdAndUpdate(idInscrip, { "etat": "refused" }, function (err, inscrit) {
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
            inscrit.etat = "refused";
            resp.status(200).json(inscrit);
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



app.get('/inscritSearch',(req:Request,res:Response)=>{
    const search = req.query.search || '';
    const page:number = parseInt(req.query.page?.toString()||'1');
    const size:number = parseInt(req.query.size?.toString()||'5');

    Inscrit.paginate({etat:{$regex:".*(?i)"+search+".*"}},{page:page,limit:size},(err:any,inscrit:any)=>{
        if(err) res.status(500).send(err);
        else res.send(inscrit);
    });
    
});
*/
app.get("/", function (req, resp) {
    resp.send("hello express !");
});
