module.exports = {

  title: '曾大人的博客后台管理',

  /**
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: false,

  /**
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: false,


  baseURL:process.env.NODE_ENV==="development"?"localhost:8080":"www.zxl.link:8080"
}
