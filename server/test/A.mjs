// A.js
import B from './B.mjs';


function printNameA() {
    console.log("My name is A");
    B.printNameB();  // 调用B.js中的方法
}


export default { printNameA };
