// var index
// 서로다른 js 파일내 변수, 함수들이 동일한 스코프로 인해 덮어씌워지지 않도록
// index 객체 내에서만 function이 유효함
var main = {
    init : function(){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
    },
    save: function(){
       var data = {
           title : $('#title').val(),
           author : $('#author').val(),
           content : $('#content').val(),
       };

       $.ajax({
           type: 'POST',
           url: '/api/v1/posts',
           dataType: 'json',
           contentType: 'application/json; charset=UTF-8',
           data: JSON.stringify(data)
       }).done(function(){
           alert('글이 등록되었습니다.');
           window.location.href='/';
       }).fail(function(error){
           alert(JSON.stringify(error));
        });
    }
};

main.init();