const query = require('../utils/utils');

function getType(type) {
    if (type == 1) {
        return '玄幻'
    } else if (type == 2) {
        return '修真'
    } else if (type == 3) {
        return '都市'
    } else if (type == 4) {
        return '历史'
    } else if (type = 5) {
        return '网游'
    }
}

const router = (req, res) => {
    let type = getType(req.query.type)
    query(`select *
           from booklist
           where type = '${type}'`, [1], (err, result) => {
        if (err) throw err
        res.send(result)
    })
}
module.exports=router
