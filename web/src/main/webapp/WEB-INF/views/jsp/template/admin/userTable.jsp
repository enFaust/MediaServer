<%--User table --%>
<script id="user-table" type="text/x-handlebars-template">
    <br>
    <br>
    <br>
    <h1 class="page-header"><fmt:message key="admin.table.title.users"/></h1>
    <a data-toggle="modal" data-target="#newUser" href="#" class="btn btn-primary btn-xs pull-right"><b>+</b>
        <fmt:message key="admin.table.add.user"/></a>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th class="text-center"><fmt:message key="admin.table.user.login"/></th>
            <th class="text-center"><fmt:message key="admin.table.user.name"/></th>
            <th class="text-center"><fmt:message key="admin.table.user.surname"/></th>
            <th class="text-center"><fmt:message key="admin.table.user.email"/></th>
            <th class="text-center"><fmt:message key="admin.table.users.photo"/></th>
            <th class="text-center"><fmt:message key="admin.table.user.role"/></th>
            <th class="text-center"><fmt:message key="admin.table.users.action"/></th>
        </tr>
        </thead>
        <tbody>
        {{#each users}}
        <tr>
            <td class="text-center">{{id}}</td>
            <td class="text-center">{{login}}</td>
            <td class="text-center">{{name}}</td>
            <td class="text-center">{{surname}}</td>
            <td class="text-center">{{email}}</td>
            <td class="text-center"><img src="{{photo}}" class="round" style="width: 100px; height: 100px;"></td>
            <td class="text-center">{{role.name}}</td>
            <td class="text-center">
                <a class='btn btn-info btn-xs edit' href="#" data-toggle="modal" data-target="#editUser"
                   onmouseover="editUser('{{id}}','{{login}}','{{name}}','{{surname}}','{{email}}','{{adminRoot}}')">

                    <span class="glyphicon glyphicon-edit"></span> <fmt:message key="admin.table.edit"/>
                </a>
                <a href="#" class="btn btn-danger btn-xs" onclick="deleteMethod('admin/user/{{id}}')">
                    <span class="glyphicon glyphicon-remove"></span><fmt:message key="admin.table.delete"/>
                </a>
                <a href="#" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#orderList"
                   onclick="getMethod('admin/ordeer')">
                    <span class="glyphicon glyphicon-shopping-cart"></span><fmt:message key="admin.table.orders"/>
                </a>
            </td>
        </tr>
        {{/each}}
        </tbody>
    </table>
    <div class="bs-example">
        <ul class="pagination">
            <c:forEach var = "i" begin = "1" end = "${page}">
                <li><a href="#" onclick="commandShowWithPage('show-userDetails-list', '#userDetails-table', ${i});"> <c:out value = "${i}"/></a></li>
            </c:forEach>
        </ul>
    </div>
</script>