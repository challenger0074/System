const express = require('express');
const multer = require('multer');
const path = require('path');
const iconv = require('iconv-lite');
const app = express();
const PORT = process.env.PORT || 3000;
const cors = require('cors');

app.use(cors());
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.get('/', (req, res) => {
    res.send('Hello World!')
})
// 设置 multer 存储选项
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads/'); // 上传文件存储目录
    },
    filename: (req, file, cb) => {
        console.log(file.originalname);
        // 处理文件名为utf-8形式
        let fileNameUtf8 = iconv.decode(Buffer.from(file.originalname, 'binary'), 'utf-8');
        cb(null, fileNameUtf8);
    }
});

const upload = multer({ storage: storage });

// 创建上传处理接口
app.post('/upload', upload.single('file'), (req, res) => {
    if (!req.file) {
        return res.status(400).send('No file uploaded.');
    }
    res.send({ message: 'File uploaded successfully.', file: req.file });
});

// 创建下载文件接口
app.get('/download/:filename', (req, res) => {
    const file = path.join(__dirname, 'uploads', req.params.filename);
    res.download(file, (err) => {
        if (err) {
            res.status(404).send('File not found.');
        }
    });
});

// 启动服务器
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
