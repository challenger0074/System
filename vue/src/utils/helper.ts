
import { NTooltip, SelectOption } from "naive-ui";
import { h, VNode } from "vue";
import { useClipboard } from "@vueuse/core";

import { isEmpty } from "lodash-es";


type AnyObject = { [key: string]: any };

// 必要数据
let imageBlobURL: string = "";


// 系统判断
const userAgent = window.navigator.userAgent;
export const isWin = userAgent.includes("Windows");
export const isMac = userAgent.includes("Macintosh");
export const isLinux = userAgent.includes("Linux");
export const isElectron = userAgent.includes("Electron");


// 封面加载完成
export const coverLoaded = (e: Event) => {
  const target = e.target as HTMLElement | null;
  if (target && target.nodeType === Node.ELEMENT_NODE) {
    target.style.opacity = "1";
  }
};

