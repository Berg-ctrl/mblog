<@layout.extends name="/inc/layout.ftl">
    <@layout.put block="title">
        <title>${user.name}的关注</title>
    </@layout.put>

    <@layout.put block="contents">
        <div class="row users-show">
            <div class="col-xs-12 col-md-3 side-left">
                <@layout.extends name="/inc/user_sidebar.ftl" />
            </div>
            <div class="col-xs-12 col-md-9 side-right">
                <div class="panel panel-default">
                    <div class="panel-heading">关注的作者</div>
                    <@user_attention userId=user.id pageNo=pageNo>
                        <div class="panel-body">
                            <ul class="list-group">
                                <#list results.content as row>
                                    <li class="list-group-item" el="loop-${row.authorId}">
                                        <a href="/users/${row.authorId}" class="remove-padding-left">${row.name}</a>
                                        <a class="act" href="javascript:void(0);" data-evt="unAttention" data-id="${row.authorId}">
                                            <i class="icon icon-close"></i>
                                        </a>
                                    </li>

                                </#list>

                                <#if results.content?size == 0>
                                    <li class="list-group-item ">
                                        <div class="infos">
                                            <div class="media-heading">该目录下还没有内容!</div>
                                        </div>
                                    </li>
                                </#if>
                            </ul>
                        </div>
                        <div class="panel-footer">
                            <@utils.pager request.requestURI!'', results, 5/>
                        </div>
                    </@user_attention>
                </div>
            </div>
        </div>
        <!-- /end -->

        <script type="text/javascript">
            $(function() {
                $('a[data-evt=unAttention]').click(function () {
                    var id = $(this).attr('data-id');

                    layer.confirm('确定删除此项吗?', {
                        btn: ['确定','取消'], //按钮
                        shade: false //不显示遮罩
                    }, function(){
                        jQuery.getJSON('/user/unattention', {'id': id}, function (ret) {
                            layer.msg(ret.message, {icon: 1});
                            if (ret.code >=0) {
                                $('#loop-' + id).fadeOut();
                                $('#loop-' + id).remove();
                            }
                        });

                    }, function(){

                    });
                });
            })
        </script>

    </@layout.put>
</@layout.extends>