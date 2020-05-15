(function () {
  "use strict";

  var page = {
    ready: function () {
      $(".delete-item").click(function () {
        var formName = `#imageForm${$(this).attr("id")}`;

        if (confirm("Are you sure to delete this file?")) {
          $(formName).submit();
        } else {
          return false;
        }
      });

      $("a.AS-POST").on('click', e => {
        e.preventDefault()
        let frm = document.getElementById('form' + e.target.id)
        frm.method = 'POST'
        frm.action = e.target.href
        document.body.appendChild(frm)
        frm.submit()
      })

      $(".cal-count").change(function () {
        var cid = $(this).attr("id");
        var count = $(this).val();
        updateCartCount(cid, count, totalAmount)
      })

      totalAmount()

      $("#submit").click(function (e) {
        var itemNo = $('.media-body').length
        if (itemNo === 0) {
          e.preventDefault()
          return false
        } else {
          e.submit()
        }
      })
    },
  };

  $(document).ready(page.ready);
})();

function updateCartCount(cid, count, callback) {
  $.ajax({
    url: '/items/' + cid,
    data: {
      'count': count
    },
    type: 'POST',
    success: function (data) {
      $(`#total${cid}`).html('$' + data.amount)
      callback();
    }
  });
}

function totalAmount() {
  var allmount = 0;
  $.each($("strong[id^=total]"), function (index, value) {
    var id = `${$(value).attr("id")}`
    var innerValue = document.getElementById(id).textContent
    var aa = parseFloat(innerValue.substr(1))
    allmount = allmount + aa;
  });

  $("#calAmount").html('$' + allmount.toFixed(2))
}

function back() {
  window.history.back();
}