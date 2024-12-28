// B.js
import C from './C.mjs';

function printNameB() {
    console.log("My name is B");
    C.printNameC();  // 调用C.js中的方法
}

export default { printNameB };
