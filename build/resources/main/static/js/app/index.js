// var index
// 서로다른 js 파일내 변수, 함수들이 동일한 스코프로 인해 덮어씌워지지 않도록
// index 객체 내에서만 function이 유효함
var main = {
    init : function(){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
        $('#btn-update').on('click', function(){
            _this.update();
        });
        $('#btn-delete').on('click', function(){
            _this.delete();
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
    },
    update: function(){
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+ id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8;',
            data: JSON.stringify(data),
        }).done(function(){
            // 수정 성공
            alert('글이 수정되었습니다.');
            window.location.href= '/';
        }).fail(function(err){
            // 수정실패시 에러메세지 출력
           alert(JSON.stringify(err));
        });
    },
    delete: function(){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8;'
        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.href='/';
        }).fail(function(err){
            alert(JSON.stringify(err));
        })
    }
};

main.init();