const express = require('express')
const cors = require('cors');
const app = express()
const port = 3001

// 配置 CORS
app.use(cors());
app.get('/', (req, res) => {
    res.send('Hello World!')
})


app.get('/book', require('./sql_service/book.js'));

app.get('/booklist', require('./sql_service/booklist.js'));

app.get('/titles', require('./sql_service/book_title.js'));

app.get('/type', require('./sql_service/type.js'));



app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})
