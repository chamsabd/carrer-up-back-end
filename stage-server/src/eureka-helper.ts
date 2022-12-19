const Eureka = require('eureka-client').Eureka;
// import { Config, CloudConfigOptions, ConfigObject } from 'spring-cloud-config';


// const cloudConfigOptions: CloudConfigOptions = {
//     configPath: __dirname + '/config',
//     activeProfiles: ['dev1'],
//     level: 'debug'
// };
 
// let myConfig: ConfigObject;
 
// Config.load(cloudConfigOptions).then((theConfig: ConfigObject) => {
//    myConfig = theConfig;
// console.log(myConfig);
// }
   // now run your application with the loaded config props.
   // do this by saving the returned config object somewhere,
   // or by using the Config.instance() helper.
//);
const eurekaHost = (process.env.EUREKA_CLIENT_SERVICEURL_DEFAULTZONE || '127.0.0.1');
const eurekaPort = 8761;
const hostName = (process.env.HOSTNAME || 'localhost')
const ipAddr = '172.0.0.1';

exports.registerWithEureka = function(appName:String, PORT:Number) {
    const client = new Eureka({
    instance: {
      app: appName,
      hostName: hostName,
      ipAddr: ipAddr,
      port: {
        '$': PORT,
        '@enabled': 'true',
      },
      vipAddress: appName,
      dataCenterInfo: {
        '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
        name: 'MyOwn',
      },
    },
    //retry 10 time for 3 minute 20 seconds.
    eureka: {
      host: eurekaHost,
      port: eurekaPort,
      servicePath: '/eureka/apps/',
      maxRetries: 10,
      requestRetryDelay: 2000,
    },
  })

client.logger.level('debug')

client.start( (error: any) => {
    console.log(error || "stages service registered")
});

function exitHandler(options: any, exitCode: any) {
    if (options.cleanup) {
    }
    if (exitCode || exitCode === 0) console.log(exitCode);
    if (options.exit) {
        client.stop();
    }
}

client.on('deregistered', () => {
    process.exit();
    console.log('after deregistered');
})

client.on('started', () => {
  console.log("eureka host  " + eurekaHost);
})

process.on('SIGINT', exitHandler.bind(null, {exit:true}));
};