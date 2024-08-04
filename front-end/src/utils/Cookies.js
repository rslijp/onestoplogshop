export function csrfToken(){
    const cookieStr = ""+document.cookie;
    // console.log(cookieStr);
    return cookieStr.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*=\s*([^;]*).*$)|^.*$/, '$1');
}