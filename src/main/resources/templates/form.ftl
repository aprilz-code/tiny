<!DOCTYPE html>
<html lang="en">

<head>
   <meta charset="UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>填写信息</title>
   <link rel="stylesheet" href="../css/icon/iconfont.css">
   <script src="../js/jquery-3.6.0.min.js"></script>
   <script type="text/javascript">
      $(function (){
         $('#load').css("display","none");
         $('#tip').css("display","none");



      });


      function imgChange1(e) {
            console.info(e.target.files[0]);//图片文件
            var dom =$("#front")[0];
            console.info(dom.value);//这个是文件的路径 C:fakepathicon (5).png
            console.log(e.target.value);//这个也是文件的路径和上面的dom.value是一样的
            var reader = new FileReader();
            reader.onload = (function (file) {
               return function (e) {
                  console.info(this.result); //这个就是base64的数据了
                  $("#m1")[0].src=this.result;
                  $("#a1").hide();
                  $("#m1").show();
               };
            })(e.target.files[0]);
            reader.readAsDataURL(e.target.files[0]);
      };

      function imgChange2(e) {
         console.info(e.target.files[0]);//图片文件
         var dom =$("#behind")[0];
         console.info(dom.value);//这个是文件的路径 C:fakepathicon (5).png
         console.log(e.target.value);//这个也是文件的路径和上面的dom.value是一样的
         var reader = new FileReader();
         reader.onload = (function (file) {
            return function (e) {
               console.info(this.result); //这个就是base64的数据了
               $("#m2")[0].src=this.result;
               $("#a2").hide();
               $("#m2").show();
            };
         })(e.target.files[0]);
         reader.readAsDataURL(e.target.files[0]);
      };

      function  openFront(){
         $('#front').val('');
         $('#front').click();
      }

      function  openBehind(){
         $('#behind').val('');
         $('#behind').click();
      }

      function onSubmit(){
         var tip = '';

         if($('#front').val().length <=0){
            tip = "请选择身份证正面！";
         }
         if(tip != ''){
            tip = consoleTip(tip);
            return;
         }

         if($('#behind').val().length <=0){
            tip = "请选择身份证反面！";
         }
         if(tip != ''){
            consoleTip(tip);
            return;
         }

         var tip = '';
         if($('#username') .val().length <=0){
            tip = "请填写用户名！";

         }

         if(tip != ''){
            consoleTip(tip);
            return;
         }

         if($('#phone') .val().length <=0){
            tip = "请填写手机号码！";
         }
         if(tip != ''){
            consoleTip(tip);
            return;
         }

         if($('#bankCard') .val().length <=0){
            tip = "请填写银行卡号！";

         }

         if(tip != ''){
            consoleTip(tip);
            return;
         }
         if($('#openBank') .val().length <=0){
            tip = "请填写开户行！";

         }
         if(tip != ''){
            consoleTip(tip);
            return;
         }

         if($('#companyName') .val().length <=0){
            tip = "请填写单位名称！";

         }
         if(tip != ''){
            consoleTip(tip);
            return;
         }
         if($('#workPlace') .val().length <=0){
            tip = "请填写工作地址！";
         }
         if(tip != ''){
            consoleTip(tip);
            return;
         }
         if($('#address') .val().length <=0){
            tip = "请填写居住地！";
         }
         if(tip != ''){
            consoleTip(tip);
            return;
         }
         <#if !relationId??>
            if($('#totalAmount').val().length <=0){
               tip = "请填写总额度！";
            }
         if(tip != ''){
            consoleTip(tip);
            return;
         }
         </#if>





         var formData = new FormData(document.getElementById("form"));//表单id
         $('#load').css("display","block");
         $.ajax({
            url: "/doIt",
            type: "post",
            cache: false,
            contentType: false,
            processData: false,
            data:  formData,
            //dataType:  html,
            success: function (result) {
               $('#load').css("display","none");
               if(result.code !== 200){
                  alert(result.message);
               }else{
                  var data = result.data;
                  //go result
                  if(data.relationId != '' && data.relationId != undefined ){
                     window. location. href = "/home?username=" + encodeURI(data.username) + "&totalAmount=" + data.totalAmount
                             + "&token=" + data.token+ "&relationId=" + data.relationId;
                  }else{
                     window. location. href = "/result";
                  }

               }
            },
            error : function(e){
               $('#load').css("display","none");
               alert(JSON.parse(e.responseText).message);
            }
         })
      }

      function consoleTip(tip){
         if(tip != ''){
            $("#aTip").html(tip);
            $('#tip').css("display","block");
            setTimeout(()=>{
               $('#tip').css("display","none");
               $("#aTip").html('');
            },2000)
         }
         return '';
      }
   </script>
   <style>
      html,
      body {
         margin: 0px;
         padding: 0px;
         background-color: #F7F8FA;
      }

      /* 身份证部分 */
      .id-positive,
      .id-back {
         width: 95%;
         height: 180px;
         border: dashed 1px #1989fa;
         margin: 5px auto;
         position: relative;
         border-radius: 5px;
      }

      .id-back span {
         position: absolute;
         left: 50%;
         top: 50%;
         transform: translate(-50%, -50%);
         color: #409eff;
      }

      .id-positive span {
         position: absolute;
         left: 50%;
         top: 50%;
         transform: translate(-50%, -50%);
         color: #409eff;
      }

      /* 表单部分 */
      .cell {
         position: relative;
         display: -webkit-box;
         display: -webkit-flex;
         display: flex;
         box-sizing: border-box;
         width: 100%;
         padding: 10px 16px;
         overflow: hidden;
         color: #323233;
         font-size: 14px;
         line-height: 24px;
         background-color: #fff;
      }

      .cell .field_label {
         -webkit-box-flex: 0;
         -webkit-flex: none;
         flex: none;
         box-sizing: border-box;
         width: 6.2em;
         margin-right: 12px;
         color: #409eff;
         text-align-last: justify;
         word-wrap: break-word;
      }

      .field_label::after {
         content: '：';
      }

      .field_control {
         display: block;
         box-sizing: border-box;
         width: 100%;
         min-width: 0;
         margin: 0;
         padding: 0;
         color: #323233;
         line-height: inherit;
         text-align: left;
         background-color: transparent;
         border: 0;
         resize: none;
         outline: none;
      }

      .cell_value {
         position: relative;
         overflow: hidden;
         color: #969799;
         text-align: right;
         vertical-align: middle;
         word-wrap: break-word;
      }

      .field__body {
         display: -webkit-box;
         display: -webkit-flex;
         display: flex;
         -webkit-box-align: center;
         -webkit-align-items: center;
         align-items: center;
      }

      /* 额度部分 */
      .quota {
         margin: 10px 0;
         background-color: #FFF;
         padding: 10px;
      }

      .quota_value {
         display: block;
         box-sizing: border-box;
         width: 100%;
         min-width: 0;
         margin: 0;
         padding: 0;
         color: #323233;
         line-height: 50px;
         text-align: center;
         background-color: transparent;
         border: 0;
         resize: none;
         outline: none;
         font-size: 15px;
      }

      /* 提交按钮 */
      footer {
         /* position: fixed; */
         /* bottom: 0px; */
         /* left: 0; */
         width: 100%;
         padding: 5px 0px;
         margin-top: 10px;
      }

      .button_block {
         display: block;
         width: 90%;
         color: #fff;
         margin: 0 auto;
         background-color: #1989fa;
         border: 1px solid #1989fa;
         font-size: 14px;
         padding: 10px 0px;
         border-radius: 20px;
         outline: none;
      }

      #prizeFile {
         height: 0;
         transform: translateX(-100%);
      }

      /* 底部tabber */
      .tabber {
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

      .tabbar-item {
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

      .tabbar-item_icon {
         position: relative;
         margin-bottom: 4px;
         font-size: 22px;
      }

      .block {
         width: 150px;
         height: 120px;
         background-color: rgba(0, 0, 0, 0.5);
         position: fixed;
         left: 50%;
         top: 50%;
         border-radius: 5px;
         transform: translate(-50%, -50%);
         color: #FFF;
         display: flex;
         justify-content: center;
         align-items: center;
         flex-direction: column;
      }

      .icon {
         font-size: 30px;
         animation: fadenum 1s infinite linear;
      }

      @keyframes fadenum {
         0% {
            transform: rotate(0deg);
         }

         100% {
            transform: rotate(360deg);
         }
      }

      .overlay {
         position: fixed;
         top: 0;
         left: 0;
         z-index: 1;
         width: 100%;
         height: 100%;
         /* background-color: rgba(0, 0, 0, 0.7); */
      }
   </style>
</head>

<body>
   <main id="app">
      <form  id="form"  >
         <input type="file"  id="front" name="front" accept="image/*" onchange="imgChange1(event)"  hidden>
         <input type="file" id="behind" name="behind" accept="image/*"onchange="imgChange2(event)"   hidden>
         <input name="token" value="${token}"  type="hidden">
         <input name="relationId" value="${relationId!''}"  type="hidden">

      <div class="id-positive" onclick="openFront()">
         <span  id="a1">身份证正面</span>
         <img id="m1"   class="img" ref="img" style="width: 100%;height: 100%;display: none" />
      </div>
      <div class="id-back" onclick="openBehind()">
         <span id="a2">身份证反面</span>
         <img id="m2"  class="img" ref="img" style="width: 100%;height: 100%;display: none" />
      </div>

      <section>
         <div class="form" >

            <div class="cell field">
               <div class="field_label">
                  姓名
               </div>
               <div class="cell_value">
                  <div class="field__body">
                     <input name="username" id="username" class="field_control"   placeholder="请输入姓名" type="text">
                  </div>
               </div>
            </div>
            <div class="cell field">
               <div class="field_label">
                  <span>手机号</span>
               </div>
               <div class="cell_value">
                  <div class="field__body">
                     <input id="phone"  name="phone" type="tel" class="field_control"   placeholder="请输入手机号">
                  </div>
               </div>
            </div>
            <div class="cell field">
               <div class="field_label">
                  银行卡号
               </div>
               <div class="cell_value">
                  <div class="field__body">
                     <input name="bankCard" id="bankCard" type="number"   oninput="if(value.length>24)value=value.slice(0,24)" class="field_control" placeholder="请输入银行卡号">
                  </div>
               </div>
            </div>
            <div class="cell field">
               <div class="field_label">
                  开户行
               </div>
               <div class="cell_value">
                  <div class="field__body">
                     <input name="openBank" id="openBank" class="field_control"   placeholder="请输入开户行" type="text">
                  </div>
               </div>
            </div>
            <div class="cell field">
               <div class="field_label">
                  单位名称
               </div>
               <div class="cell_value">
                  <div class="field__body">
                     <input name="companyName"  id="companyName"  class="field_control"   placeholder="请输入单位名称" type="text">
                  </div>
               </div>
            </div>
            <div class="cell field">
               <div class="field_label">
                  工作地址
               </div>
               <div class="cell_value">
                  <div class="field__body">
                     <input name="workPlace" id="workPlace" class="field_control"   placeholder="请输入工作地址" type="text">
                  </div>
               </div>
            </div>
            <div class="cell field">
               <div class="field_label">
                  居住地址
               </div>
               <div class="cell_value">
                  <div class="field__body">
                     <input name="address" id="address"  class="field_control"   placeholder="请输入居住地址" type="text">
                  </div>
               </div>
            </div>
         </div>
         <#if  relationId?? >

            <#else >
               <div class="quota" v-if="show">
                  <input name="totalAmount" id="totalAmount"  class="quota_value"   placeholder="请输入额度" type="number">
               </div>
         </#if>

         <div class="cell field">
            <div class="field_label">
               有房(车)
            </div>
            <div class="cell_value">
               <div class="field__body">
                  <label><input  name="hasCar" type="radio" value="1" />是</label>
                  <label><input  name="hasCar" type="radio" value="0"  checked="checked"/>否</label>
               </div>
            </div>
         </div>
         <div class="cell field">
            <div class="field_label">
               有公积金
            </div>
            <div class="cell_value">
               <div class="field__body">
                  <label><input  name="hasReservedFunds" type="radio" value="1" />是</label>
                  <label><input  name="hasReservedFunds" type="radio" value="0" checked="checked"/>否</label>
               </div>
            </div>
         </div>
         <div class="cell field">
            <div class="field_label">
               有保险单
            </div>
            <div class="cell_value">
               <div class="field__body">
                  <label><input  name="hasChit" type="radio" value="1" />是</label>
                  <label><input  name="hasChit" type="radio" value="0"  checked="checked"/>否</label>
               </div>
            </div>
         </div>
      </section>
      </form>
   </main>

   <footer>
      <button class="button_block" type="button"  onclick="onSubmit()">提交</button>
   </footer>

   <div style="height: 50px;"></div>
   <div class="tabber">
      <div class="tabbar-item"  @click="to">
         <div class="tabbar-item_icon">
            <i class="iconfont icon-shouye-tianchong"></i>
         </div>
         <div class="tabbar-item_text">首页</div>
      </div>
      <div class="tabbar-item"  style="color: #1989fa;">
         <div class="tabbar-item_icon">
            <i class="iconfont icon-31wodexuanzhong"></i>
         </div>
         <div class="tabbar-item_text">我的</div>
      </div>
   </div>
   <div id="load" class="overlay" v-show="loading" style="display: none">
      <div class="block">
         <i class="icon iconfont icon-jiazai"></i>
         <div style="margin-top: 5px;">加载中</div>
      </div>
   </div>
   <div id="tip" class="overlay" v-show="tips" style="display: none">
      <div class="block">
         <!-- <i class="icon iconfont icon-jiazai"></i> -->
         <div id="aTip"></div>
      </div>
   </div >
</body>

<#--   <script>-->
<#--      new Vue({-->
<#--         el:'#app',-->
<#--         data(){-->
<#--            return {-->
<#--               form:{-->
<#--                  // 开户银行-->
<#--                  bankName:'',-->
<#--                  //卡号-->
<#--                  bankNo:'',-->
<#--                  //单位名称-->
<#--                  company:'',-->
<#--                  //手机号-->
<#--                  tel:'',-->
<#--                  //姓名-->
<#--                  name:'',-->
<#--                  // 工作地址-->
<#--                  address1:'',-->
<#--                  // 居住地址-->
<#--                  address2:'',-->
<#--                  //额度-->
<#--                  quota:'',-->
<#--                  value1:1,-->
<#--                  value2:1,-->
<#--                  value3:1,-->
<#--               },-->
<#--               show:true,-->
<#--               loading:false,-->
<#--               tips:false,-->
<#--               isLoading:false-->
<#--            }-->
<#--         },-->
<#--         mounted(){-->
<#--           let form = JSON.parse(sessionStorage.getItem("form"))-->
<#--           if(form){-->
<#--               this.show = false-->
<#--               this.form = {...form}-->
<#--               this.isLoading = true-->
<#--           }-->
<#--         },-->
<#--         methods:{-->
<#--             openPhotoAlbum(type) {-->
<#--               let prizeFile = document.getElementById('prizeFile')-->
<#--               prizeFile.value = ''-->
<#--               prizeFile.click()-->
<#--            },-->
<#--            getPicture(e) {-->
<#--               console.log('e', e)-->
<#--            },-->
<#--            onSubmit(){-->
<#--               if(this.isLoading){-->
<#--                  this.loading = true-->
<#--                  setTimeout(()=>{-->
<#--                     this.loading = false-->
<#--                     window.location.href='result.ftl';-->
<#--                  },3000)-->
<#--                  return-->
<#--               }-->
<#--               let bool = false-->
<#--               for (const key in this.form) {-->
<#--                 if(this.form[key] == ''){-->
<#--                    bool = true-->
<#--                     break-->
<#--                 }-->
<#--               }-->
<#--               if(bool){-->
<#--                  this.tips = true-->
<#--                  setTimeout(()=>{-->
<#--                     this.tips = false-->
<#--                  },500)-->
<#--                  return-->
<#--               }-->
<#--               sessionStorage.setItem("form", JSON.stringify(this.form));-->
<#--               window.location.href='home.ftl';-->
<#--            },-->
<#--            to(){-->
<#--               window.location.href='home.ftl';-->
<#--            }-->
<#--         }-->
<#--      })-->
<#--   </script>-->

</html>