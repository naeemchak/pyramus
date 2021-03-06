var categories = JSDATA["categories"].evalJSON();

function addStudyProgrammesTableRow() {
  var table = getIxTableById('studyProgrammesTable');
  var rowIndex = table.addRow([ '', '', '', '', -1]);
  for ( var i = 0; i < table.getColumnCount(); i++) {
    table.setCellEditable(rowIndex, i, true);
  }
  $('noStudyProgrammesAddedMessageContainer').setStyle({
    display : 'none'
  });
}

function onLoad(event) {
  new IxTable($('studyProgrammesTable'), {
    id : "studyProgrammesTable",
    columns : [
     {
       header : getLocale().getText("system.setupwizard.studyprogrammes.studyProgrammesTableCodeHeader"),
       left : 8,
       width: 75,
       dataType : 'text',
       editable : true,
       paramName : 'code'
     }, {
       header : getLocale().getText("system.setupwizard.studyprogrammes.studyProgrammesTableCategoryHeader"),
       left : 91,
       width : 200,
       dataType : 'select',
       editable : true,
       paramName : 'category',
       options : (function() {
         var result = [];
         for ( var i = 0, l = categories.length; i < l; i++) {
           result.push({
             text : categories[i].name,
             value : categories[i].id
           });
         }
         return result;
       })()
     }, {
      header : getLocale().getText("system.setupwizard.studyprogrammes.studyProgrammesTableNameHeader"),
      left : 299,
      right : 46,
      dataType : 'text',
      editable : true,
      paramName : 'name',
      required : true
    }, {
      right : 8,
      width : 30,
      dataType : 'button',
      imgsrc : GLOBAL_contextPath + '/gfx/list-remove.png',
      tooltip : getLocale().getText("system.setupwizard.studyprogrammes.studyProgrammesTableRemoveTooltip"),
      onclick : function(event) {
        event.tableComponent.deleteRow(event.row);
        if (event.tableComponent.getRowCount() == 0) {
          $('noStudyProgrammesAddedMessageContainer').setStyle({
            display : ''
          });
        }
      },
      paramName : 'removeButton',
      hidden : false
    }, {
      dataType : 'hidden',
      paramName : 'studyProgrammeId'
    }]
  });
  
  addStudyProgrammesTableRow();
}
