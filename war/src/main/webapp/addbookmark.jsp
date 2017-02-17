<%@include file="top.jsp"%>

<h2>Add a Bookmark</h2>

<form action="${context}/bookmark/added" method="post">
	<table>
		<tr>
			<th align="left">Address</th>
			<td><input type="text" id="address" name="address" size="75"
				maxlength="65535" value="bAddress" onblur="useAddress(this)" /></td>
			<td>* Required</td>
		</tr>
		<tr>
			<th align="left">Title</th>
			<td><input type="text" id="titleField" name="title" size="75"
				maxlength="255" value="bTitle"
				onkeypress="this.style.backgroundImage = 'none';" /></td>
			<td>* Required</td>
		</tr>
		<tr>
			<th align="left">Description <a
				onclick="var nz = document.getElementById('privateNoteZone'); nz.style.display='';this.style.display='none';">
					Add Note
			</a>
			</th>
			<td><textarea name="description" id="description" rows="5"
					cols="63">bDescription</textarea></td>
			<td>* You can use anchors to delimite attributes. for example:
				[publisher]blah[/publisher] 
			</td>
		</tr>

		<tr>
			<th align="left">Tags</th>
			<td class="scuttletheme"><input type="text" id="tags"
				name="tags" size="75" value="tags" /></td>
			<td>* Comma-separated</td>
		</tr>
		<tr>
			<th></th>
		</tr>
		<tr>
			<th align="left">Privacy</th>
			<td><select name="status">
					<option value="0">Public</option>
					<option value="1">Shared with Watch List</option>
					<option value="2">Private</option>
			</select></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="submitted" value="btnsubmit" />
				<input type="button" name="cancel" value="Cancel"
				onclick="window.close();':'javascript: history.go(-1)'" /> <input
				type="submit" name="delete" value="Delete Bookmark" /></td>
			<td></td>
		</tr>
	</table>
</form>

<link
	href="<?php echo ROOT ?>js/jquery-ui-1.8.11/themes/base/jquery.ui.all.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="<?php echo ROOT ?>js/jquery-ui-1.8.11/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<?php echo ROOT ?>js/jquery-ui-1.8.11/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="<?php echo ROOT ?>js/jquery-ui-1.8.11/jquery.ui.position.js"></script>
<script type="text/javascript"
	src="<?php echo ROOT ?>js/jquery-ui-1.8.11/jquery.ui.autocomplete.js"></script>
<script type="text/javascript">
//<![CDATA[
jQuery(document).ready(function() {
    function split(val)
    {
        return val.split(/[,=><]\s*/);
    }

    function extractLast(term)
    {
        return split(term).pop();
    }
    //var availableTags = ["c++", "java", "php", "coldfusion", "javascript", "asp", "ruby"];

    jQuery("input#tags").autocomplete({
        autoFocus: true,
        minLength: 1,

        source: function(request, response) {
            // delegate back to autocomplete, but extract the last term
            var term = extractLast(request.term);
            if (term.length < this.options.minLength) {
                return;
            }
            response(
                /*
                $.ui.autocomplete.filter(
                    availableTags, extractLast(request.term)
                )
                */
                $.getJSON(
                    "<?php echo $ajaxUrl; ?>",
                    { beginsWith: term },
                    response
                )
            );
        },

        focus: function() {
            // prevent value inserted on focus
            return false;
        },
        select: function(event, ui) {
            var terms = split(this.value);
            // remove the current input
            terms.pop();
            // add the selected item
            terms.push(ui.item.value);
            // add placeholder to get the comma-and-space at the end
            terms.push("");
            this.value = terms.join(", ");
            return false;
        }
    });
});
//]]>
</script>

<h3>Import</h3>
<ul>
	<li><a href="${context}/importNetscape">Import bookmarks from bookmark file	</a> Internet Explorer, Mozilla Firefox and Netscape
</ul>



