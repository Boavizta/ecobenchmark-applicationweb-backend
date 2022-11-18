const fs = require('node:fs');

const config = JSON.parse(fs.readFileSync('./config.json'));

const officialRepo = process.env.GITHUB_REPOSITORY === 'Boavizta/ecobenchmark-applicationweb-backend';
const usecaseFromEnv = (() => {
    if (process.env.GITHUB_REF) {
        if (process.env.GITHUB_REF === 'ref/head/main') {
            return 'default';
        }
        if (process.env.GITHUB_REF.startsWith('ref/head/usecase-')) {
            return process.env.GITHUB_REF.substr(17);
        }
    }
    return 'other';
})();
const canPushImage = officialRepo && config.usecase === usecaseFromEnv;

console.log(`::set-output name=usecase::${config.usecase}`);
console.log(`::set-output name=push::${canPushImage}`);
