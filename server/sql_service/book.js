const query = require('../utils/utils')

const router=(req,res)=>{
    let  book=req.query.book
    let  id=req.query.id
    query(`select * from book${book} where id=${id}`,[1],
        (err,results,field)=>{
        if (err)throw err
        res.send(results[0])
        }

    )
}
module.exports=router
