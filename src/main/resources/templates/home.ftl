<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>首页</title>
   <link rel="stylesheet" href="../css/icon/iconfont.css">
   <style>
      html,body{
         /* background-color: #F7F8FA; */
         margin: 0;
         padding: 0;
         height: 100%;
      }
      h2{
         margin: 0;
         padding: 0;
      }
      /* #app{
         height: 100%;
      } */
      .top{
         /* background-color: #; */
         background-image: linear-gradient(120deg, #0B8FFC 0%, #66a6ff 100%);
      }
      .top .user-info {
         display: flex;
         justify-content: flex-start;
         align-items: center;
         padding: 40px 15px;
      }
      .top .user-info img{width: 50px;height: 50px;border-radius: 50%;margin-right: 10px;}
      .top .user-info span{ color: #FFF;font-size: 14px; }
        
      section{background-color: #FFF; transform: translateY(-20px);border-radius: 20px 20px 0px 0px ; padding-top: 20px; }
      section .title{text-align: center;color: #0B8FFC;font-size: 20px;margin-bottom: 10px;}
      .success_box {border-radius: 20px 20px 0px 0px ;}
      .success {font-size: 14px; position: absolute;right: 0px; top: -20px; background-color: #F2F2F4;color: #76C17B;border-bottom-left-radius: 20px; border-top-right-radius: 20px; padding: 5px 20px;}
      .type{text-align: center;margin-bottom: 20px;}
      .type span{border: 1px solid #B6B5BD;padding: 2px 15px;font-size: 13px;color: #B6B5BD;border-radius: 15px; }
      /* 中间圆环 */
      .content-quota{
         margin-top: 10px;
         text-align: center;
      }
      .circle {
         width: 100%;
         height: 300px;
         border-radius: 50%;
         background: linear-gradient(#0665F7 0%,#FFF 65% );
         position: relative;
         margin: 0 auto;
      }
      .content {
         position: absolute;
         /* left: 13px; */
         /* top: 13px; */
         left: 50%;
         top: 50%;
         transform: translate(-50%, -50%);
         width: 295px;
         height: 295px;
         border-radius: 50%;
         background-color: #fff;
      }
      .box {
         height: 100%;display: flex;
         justify-content: space-around;
         flex-direction: column;
      }
      .button_block{
         display: block;
         width: 90%;
         color: #fff;
         margin: 0 auto;
         background-color: #1989fa;
         border: 1px solid #1989fa;
         font-size: 14px;
         padding: 10px 0px;
         border-radius: 20px;
         outline:none;
      }
      .quota_title{padding-top: 30px;color: #9C9C9F;font-size: 15px;}
      .quota_value{color: #F05149;font-size: 30px;margin: 10px 0; }
      .rate_title{color: #9C9C9F;font-size: 15px;margin: 10px 0; }
      .rate_value{color: #F05149;font-size: 30px; }
      .end_title{font-size: 15px;color: #27ACEF;margin: 10px 0;}

      /* 底部菜单 */
      .meun{display: flex;justify-content: space-around;}
      .meun .item {color: #9C9C9F;}
      .meun .item .item_box{margin: 0 auto; width: 50px;height: 50px; line-height: 50px; text-align: center; border-radius: 50%;background-color: #EDECF3;margin-bottom: 10px;}
      .meun .item .item_box i{text-align: center;font-size: 30px;}

      /* 底部tabber */
      .tabber{
         z-index: 1;
         display: -webkit-box;
         display: -webkit-flex;
         display: flex;
         box-sizing: content-box;
         width: 100%;
         height: 50px;
         background-color: #fff;
         position: fixed;
         bottom: 0;
         left: 0;
      }
      .tabbar-item{
         display: -webkit-box;
         display: -webkit-flex;
         display: flex;
         -webkit-box-flex: 1;
         -webkit-flex: 1;
         flex: 1;
         -webkit-box-orient: vertical;
         -webkit-box-direction: normal;
         -webkit-flex-direction: column;
         flex-direction: column;
         -webkit-box-align: center;
         -webkit-align-items: center;
         align-items: center;
         -webkit-box-pack: center;
         -webkit-justify-content: center;
         justify-content: center;
         color: #646566;
         font-size: 12px;
         line-height: 1;
         /* cursor: pointer; */
      }
      .tabbar-item_icon{
         position: relative;
         margin-bottom: 4px;
         font-size: 22px;
      }

   </style>
</head>
<body>
   <main id="app">
      <div class="top">
         <div class="user-info">
            <img src="../imgs/head.png" alt="">
            <span>${username!}</span>
         </div>
      </div>
      <section>
         <div class="success_box" style="position:relative;">
            <div class="success"> <i class="iconfont icon-chenggong"></i>初审通过</div>
         </div>
         <h2 class="title">富贵融</h2>
         <div class="type">
            <span> <i class="iconfont icon-shiwu-gouwuche"></i>消费贷</span>
         </div>
         <div class="content-quota">
            <div class="circle" :style="{width:style + 'px',height:style + 'px'}">
               <div class="content" :style="{width:(style - 15) + 'px',height:(style - 15) + 'px'}">
                  <div class="box">
                      <div class="wrap">
                        <div class="quota_title">初审额度</div>
                        <h2 class="quota_value">${totalAmount!}</h2>
                        <div class="rate_title">初审年利率</div>
                        <h2 class="rate_value">*</h2>
                      </div>
                      <div class="end">
                        <div class="end_title">需要线下补充经济联系人</div>
                        <button class="button_block"  @click="to">去补充材料</button>
                      </div>
                  </div>
               </div>
            </div>
         </div>
         <div class="meun">
            <div class="item">
               <div class="item_box">
                  <i class="iconfont icon-calendar"></i>
               </div>
               <div>秒申秒贷</div>
            </div>
            <div class="item">
               <div class="item_box">
                  <i class="iconfont icon-zhongbiao"></i>
               </div>
               <div>极速到账</div>
            </div>
            <div class="item">
               <div class="item_box">
                  <i class="iconfont icon-shoujichongzhi"></i>
               </div>
               <div>随借随还</div>
            </div>
         </div>
      </section>
      <div style="height: 50px;"></div>
      <div class="tabber">
            <div class="tabbar-item" style="color: #1989fa;">
               <div class="tabbar-item_icon">
                  <i class="iconfont icon-shouye-tianchong"></i>
               </div>
               <div class="tabbar-item_text">首页</div>
            </div>
            <div class="tabbar-item"  @click="to">
               <div class="tabbar-item_icon">
                  <i class="iconfont icon-31wodexuanzhong"></i>
               </div>
               <div class="tabbar-item_text">我的</div>
            </div>
      </div> 
   </main>
   <script src="../js/vue.js"></script>
   <!-- <script src="../js/vant.js"></script> -->
   <script>
      new Vue({
         el:'#app',
         data(){
            return {
               style:300
            }
         },
         mounted(){
            this.style = document.body.offsetWidth - 50
         },
         methods:{
            to(){
               window.location.href='/form?token=${token!}&relationId=${relationId!}';
            }
         }
      })
   </script>
</body>
</html>