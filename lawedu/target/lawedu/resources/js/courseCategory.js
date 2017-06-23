$(function(){

	// 获取理论课列表，该列表和完整列表通用

	$.ajax({
	    url:lawedu+'user/getCourseTheoryList',
	    timeout:8000, //超时时间设置，单位毫秒
	    type: 'POST',  //将要使用的HTTP方法，为POST
	    dataType: 'json',
		//	    data:data,
	    success: function (theoryData) {
			 // console.log(theoryData);
			var theoryList = theoryData.list;
			var obj1 = theoryList.slice(0,6);
            // console.log(theoryListTo);
            var html1 = template("temp1",{theoryListTo:obj1});
            // console.log(html1);
            $("#theoryList").html(html1);


            // 获取实践课列表，该列表和完整列表通用
            $.ajax({
                url:lawedu+'user/getCoursePracticeList',
                timeout:8000, //超时时间设置，单位毫秒
                type: 'POST',  //将要使用的HTTP方法，为POST
                dataType: 'json',
				//	    data:data,
                success: function (practiceData) {
                    // console.log(practiceData);
                    var practiceData = practiceData.list;
                    var obj1 = practiceData.slice(0,6);
                    var html2 = template("temp2",{practiceDataTo:obj1});
                    $("#practiceList").html(html2);

                    // 获取视频课列表，该列表和完整列表通用
                    $.ajax({
                        url:lawedu+'user/getCourseVideoList',
                        timeout:8000, //超时时间设置，单位毫秒
                        type: 'POST',  //将要使用的HTTP方法，为POST
                        dataType: 'json',
						//	    data:data,
                        success: function (videoData) {
                             // console.log(videoData);
                            var videoList = videoData.list;
                            var obj1 = videoList.slice(0,6);
                            var html3 = template("temp3",{videoDataTo:obj1});
                            $("#videoList").html(html3);
                        },
                        error: function (e) {

                            alert("服务无法访问!");

                        }
                    });
                },
                error: function (e) {

                    alert("服务无法访问!");

                }
            });
		},
		error: function (e) {
			
		    alert("服务无法访问!");
		                
		}
	});

})
