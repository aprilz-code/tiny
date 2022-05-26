<!DOCTYPE html>
<html lang="en">

<head>
   <meta charset="UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>审批结果</title>
   <link rel="stylesheet" href="../css/icon/iconfont.css">

   <style>
      html,
      body {
         padding: 0px;
         margin: 0px;
         background-color: #F7F8FA;
         height: 100%;
      }

      .result {
         text-align: center;
         height: 100%;
         background-image: linear-gradient(0deg, rgba(255, 255, 255, 0.6) 0%, rgba(26, 135, 246, 1) 100%);
      }

      .box {
         width: 90%;
         padding: 30px 0px;
         background-color: #FFF;
         margin: 0 auto;
         border-radius: 10px;
         transform: translateY(10px);
      }

      .result_type span {
         font-size: 18px;
         font-weight: bold;
         color: #36A5ED;
      }

      .tips {
         margin-top: 10px;
      }

      .tips span {
         font-size: 15px;
         font-weight: bold;
      }
      .overlay{
         position: fixed;
         top: 0;
         left: 0;
         z-index: 1;
         width: 100%;
         height: 100%;
         /* background-color: rgba(0, 0, 0, 0.7); */
      }
      .block{
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
      .icon{
         font-size: 30px;
         animation:fadenum 1s infinite linear; 
      }
       @keyframes fadenum{
         0%{transform:rotate(0deg);}
         100%{transform:rotate(360deg);}
      }
   </style>
</head>

<body>
   <!-- <div class="overlay">
      <div class="block">
         <i class="icon iconfont icon-jiazai"></i>
         <div style="margin-top: 5px;">加载中</div>
      </div>
   </div> -->
   <div class="result">
      <div class="top">
         <div class="box">
            <div class="result_type">
               <span>审批结果</span>
            </div>
            <div class="tips">
               <span>综合评分不足 审批未通过</span>
            </div>
         </div>
      </div>
   </div>
</body>

</html>