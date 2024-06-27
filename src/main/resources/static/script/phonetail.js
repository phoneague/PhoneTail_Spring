$(function(){
	$('.hmenu').click(function(){
		$('.hmenu div').toggleClass('active');
		$('.gnb').toggle(300, function(){});
	});
});

function go_search(command) {
	var form = document.forms[0];
	var key = document.getElementsByName("key");
	form.action = command + "?page=1&key="+form.name;
	form.submit();
}

