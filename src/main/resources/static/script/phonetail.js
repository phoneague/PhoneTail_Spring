$(function(){
	$('.hmenu').click(function(){
		$('.hmenu div').toggleClass('active');
		$('.gnb').toggle(300, function(){});
	});
});