const fs = require('node:fs/promises');

fs.readdir('./service', { withFileTypes: true })
    .then((entries) => {
        const dir_names = entries
            .filter((entry) => entry.isDirectory())
            .map((entry) => entry.name);
        console.log(`::set-output name=services::${JSON.stringify(dir_names)}`);
    })
    .catch((err) => {
        console.error(err);
    });
