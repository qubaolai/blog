(function ($) {
    function PreLoad(imgs, options) {
        this.imgs = (typeof imgs === 'string') ? [imgs] : imgs;
        this.opts = $.extend({}, PreLoad.DEFAULTS, options);
        if(this.opts.order === 1){
            this._ordered();
        }else{
            this._unOrdered();
        }
    }
    PreLoad.DEFAULTS = {
        each :null,
        all :null,
        order : 1  //1代表有序加载 2代表无序加载
    };
    
    PreLoad.prototype._unOrdered = function () {
        var imgs = this.imgs,
            opts = this.opts,
            count = 0,
            len = imgs.length;


        imgs.each(function (i, src) {
            var img = new Image();
            $(img).on('load error',function () {
                opts.each && opts.each(count);
                if(count >= len-1){
                    opts.all && opts.all();
                }
                count ++;
            })
            img.src = src;
        })
        
    }
    
    PreLoad.prototype._ordered = function () {
        var imgs = this.imgs,
            opts = this.opts,
            count = 0,
            len = imgs.length;

        load();
        function load() {
            var img = new Image();
            $(img).on('load error',function () {
                opts.each && opts.each(count);
                if(count >= len-1){
                    opts.all && opts.all();
                }else{
                    load();
                }
                count ++;
            })
            img.src = imgs[count];
        }

    }
    
    $.extend({
        preLoad : function (imgs, opts) {
            new PreLoad(imgs, opts);
        }
    })
})(jQuery);

