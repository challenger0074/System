const query = require('../utils/utils');

const router = (req, res) => {
    const id = req.query.id || 0;
    const sql = id ? `SELECT * FROM booklist WHERE id = ?;` : `SELECT * FROM booklist;`;

    query(sql, id ? [id] : [], (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Database error', details: err });
        }

        if (id && results.length > 0) {
            res.status(200).send(results[0]); // Return the specific book if found
        } else {
            res.status(200).send(results); // Return all books
        }
    });
}

module.exports = router;
