$(document).ready(function () {

    $("textarea").val("");
    $(".fallHeart").find("img").css({
        "top": "-4%",
        "display": "block",
        "width": "20px",
        "height": "20px",
        "position": "fixed",
        "z-index": "20"
    });
    var width = $(".heart").css("width");
    var height = $(".heart").css("height");
    var bottom = $(".heart").css("bottom");
    var right = $(".heart").css("right");
    var opacity = $(".heart").css("opacity");

    setInterval(heart, 500);
    function heart() {
        if (width == "15px") {
            width = "20px";
        } else {
            width = "15px";
        }
        if (height == "15px") {
            height = "20px";
        } else {
            height = "15px";
        }
        if (bottom == "72px") {
            bottom = "70px";
        } else {
            bottom = "72px";
        }
        if (right == "277px") {
            right = "274.8px";
        } else {
            right = "277px";
        }
        if (opacity == 0.3) {
            opacity = 1;
        } else {
            opacity = 0.3;
        }

        $(".heart").css(
            {
                "width": width,
                "height": height,
                "bottom": bottom,
                "right": right,
                "opacity": opacity
            }
        )

    }

    $("textarea").focus(function () {
        $(this).attr("placeholder", "");
    })
    $("textarea").blur(function () {
        $(this).attr("placeholder", "请可爱善良美丽大方的你写下你想对(狗)成杰说的话吧~");
    })

})

function publishMessage() {
    var content = $("textarea").val();
    if (content == "") {
        alert("您还没有输入任何东西喔~");
    } else {
        $.post("php/publish.php", {
            content: content
        }, function (data) {
            $(".messageContent").html(data);
            $("textarea").val("");
        })
    }

}

function showFace() {
    $(".expressionImg").slideToggle();
}

function publishImg(i) {
    $(".expressionImg").slideToggle();
    var img = $(".expressionImg").find("img").eq(i).attr("src");
    $.post("php/publishImg.php", {
        img: img
    }, function (data) {
        $(".messageContent").html(data);
    })
}

$(function () {
    var imgs = $("img"),
        len = imgs.length,
        count = 0;

    $.preLoad(imgs, {
        each: function (count) {
            $(".loadNum").text(Math.round((count + 1) / len * 100 )+ '%');
        },
        all: function () {
            $(".load").hide();
        },
        order: 1  //1代表有序加载 2代表无序加载
    })
})


