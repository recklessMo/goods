//用于提供常用的数学计算
//比如最大值,最小值,平均值,标准差
//循环尽量小

function calc(ret, array, callback){
    for(var i = 0; i < array.length; i++){
        ret = callback(array[i], ret);
    }
    return ret;
}

(function () {
    Array.prototype.max = function(){
        return calc(0, this, function(item, max){
            return item > max ? item : max;
        });
    }

    Array.prototype.min = function(){
        return calc(999999,this, function(item, min){
            if(item < min){
                return item;
            }
            return min;
        });
    }

    Array.prototype.totalScore = function(){
        return calc(0, this, function(item, total){
            return total + item;
        });
    }

    Array.prototype.totalCount = function(){
        return calc(0, this, function(item, total){
            return total + 1;
        });
    }

    Array.prototype.scoreGap = function(full, best, good, qualified){
        var result = {full:0, best:0, good:0, qualified:0};
        for(var i = 0; i < this.length; i++){
            if(this[i] >= qualified){
                result.qualified += 1;
            }
            if(this[i] >= good){
                result.good += 1;
            }
            if(this[i] >= best){
                result.best += 1;
            }
            if(this[i] >= full){
                result.full += 1;
            }
        }
        return result;
    }

})();
