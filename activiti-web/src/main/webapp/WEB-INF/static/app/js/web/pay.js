$(function () {
    // 打赏
    var isHiddenPay = true;

    $(".detail-pay > a").click(function () {
        if (isHiddenPay) {
            $(".detail-pay > div").removeClass("hidden");
        } else {
            $(".detail-pay > div").addClass("hidden");
        }

        isHiddenPay = !isHiddenPay;

        return false;
    });

    $(".detail-pay > div > div.channel > img").mouseover(function () {
        $text = $(this).parent().find("div");
        payOut();
        taskPay = setInterval("payIn()", 5);
    }).mouseout(function () {
        payOut();
    });
});

var taskPay, degPay = 0;
var $text;
var temp = 10;

function payOut() {
    clearInterval(taskPay);
    degPay = 0;
    var t = $text[0];
    t.style.transform = "rotate(" + degPay + "deg)";
    t.style.webkitTransform = "rotate(" + degPay + "deg)";
    t.style.OTransform = "rotate(" + degPay + "deg)";
    t.style.MozTransform = "rotate(" + degPay + "deg)";
}

function payIn() {
    degPay += temp;
    if (degPay >= 50 || degPay <= -50) {
        temp *= -1;
    }

    var t = $text[0];
    t.style.transform = "rotate(" + degPay + "deg)";
    t.style.webkitTransform = "rotate(" + degPay + "deg)";
    t.style.OTransform = "rotate(" + degPay + "deg)";
    t.style.MozTransform = "rotate(" + degPay + "deg)";

}