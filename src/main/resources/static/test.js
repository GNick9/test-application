;(function($, undefined) {
    $(function() {

        $('#customers a').on('click', function() {
            document.cookie='customerID=' + $(this).data('customer');
        })

        var basket = $('#basket');

        var checkout = $('#checkout');

        checkout.attr('disabled', basket.children().length == 0);

        $('#products input[type=checkbox]').on('click', function() {
            var cb = $(this);
            if (cb.is(':checked')) {
                basket.append('<li id="b' + cb.attr('id') + '">' + cb.next().text() + '</li>')
            } else {
                $('#b' + cb.attr('id')).remove();
            }
            checkout.attr('disabled', basket.children().length == 0);
        }).filter(':checked').each(function(index) {
            var cb = $(this);
            basket.append('<li id="b' + cb.attr('id') + '">' + cb.next().text() + '</li>')
        });
    });
}(window.jQuery));