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


    </@layout.put>
</@layout.extends>