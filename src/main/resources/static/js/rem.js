// 网页字体自适应大小
!(function (win, doc) {
   function setFontSize () {
     var winWidth = window.innerWidth
     var size = (winWidth / 640) * 100
     doc.documentElement.style.fontSize = (size < 100 ? size : 100) + 'px'
   }
   var evt = 'onorientationchange' in win ? 'orientationchange' : 'resize'
   var timer = null
   win.addEventListener(evt, function () {
     clearTimeout(timer)
     timer = setTimeout(setFontSize, 300)
   }, false)
   win.addEventListener('pageshow', function (e) {
     if (e.persisted) {
       clearTimeout(timer)
       timer = setTimeout(setFontSize, 300)
     }
   }, false)
   setFontSize()
 }(window, document))
 