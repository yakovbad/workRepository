$(function() {
    var lang = window.location;
    lang = lang.toString().substr(lang.toString().length - 2);
    var field1 = [[],[],[],[],[],[],[],[],[],[]];
    var index_array = [];
    var ri = 10; // rotate_index
    var possible = false;
    var stage = 1;
    var run = 1;
    var hit_list = [];

    var i = 0;
    while (i != 100) {
        hit_list.push(i);
        i++;
    }
    i = 0;
    while (i != 10) {
        var j = 0;
        while (j != 10) {
            $("#field_1").append('<div></div>');
            $("#field_enemy").append('<div></div>');
            j++;
        }
        i++;
    }
    var cell = $("#field_1 div");
    var enemy_cell = $("#field_enemy div");

    // Перевод
    if (lang == 'en') {
        $(".symb_a").html('A');
        $(".symb_b").html('B');
        $(".symb_c").html('C');
        $(".symb_d").html('D');
        $(".symb_e").html('E');
        $(".symb_f").html('F');
        $(".symb_g").html('G');
        $(".symb_h").html('H');
        $(".symb_i").html('I');
        $(".symb_j").html('J');
        $(".header").html('Step 1: Place ships on the field');
        $(".start_help").html('Select a ship and place it on the field:<br>Use [R] key to turn it.<br>');
        $("#ship_random").html('Randomize');
        $(".l_you").html('You');
        $(".l_computer").html('Computer');
        $(".l_again").html('Play again?');
    }

    // Для атаки врага
    enemy_cell.hover(function() {
        var index = $(this).index();
        var symbol = get_symbol(index);
        var num = get_num(index);
        $("#log").html(symbol + '' + num);
        $("#log").html(index + " " + $("#log").html());
    });

    // ход
    enemy_cell.click(function() {
        if (stage == 2 && run == 1) {
            // Попадание по пустоте
            $(".clear_hit").remove();
            $(".explode_hit").remove();
            if(run == 1 && !$(this).children().hasClass('shot') && !$(this).children().hasClass('ship_part')) {
                $(this).append('<span class="shot"></span>');
                $(this).append('<span class="clear_hit"></span>');
                run = 2;
            }

            // Попадание в корабль
            if(run == 1 && $(this).children().hasClass('ship_part') && !$(this).children().children().hasClass('hit')) {
                $(this).children().append('<span class="hit"></span>');
                $(this).append('<span class="explode_hit"></span>');
                check_destroy($(this).index(), 'enemy_field');
                check_victory();
            }

            $(".clear_hit").hide( "explode", {pieces: 8 }, 200).remove();
            $(".explode_hit").hide( "explode", {pieces: 8 }, 200).remove();

            if (run == 2) {
                computer_run();
            }
        }
    });

    // Для размещения кораблей
    cell.hover(function() {
        var index = $(this).index();
        var symbol = get_symbol(index);
        var num = get_num(index);
        $("#log").html(symbol + '' + num);
        $("#log").html(index + " " + $("#log").html());
        cell.removeClass('hover');
        if ($(".selected_ship").length && stage == 1) {
            $(this).addClass('hover');
            cursor_pointer();
        }
    });

    // Размещение кораблей
    cell.click(function() {
        if ($(".selected_ship").length && possible == true) {
            // Размещяем корабль
            for (var element in index_array ) {
                $(cell[index_array[element]]).append('<span class="ship_part ship_part_display"></span>');
            }
            clear_zone();

            var cur_ship = $(".selected_ship");
            cur_ship.removeClass('selected_ship').addClass('placed_ship');
            if (!cur_ship.next().hasClass('placed_ship')) cur_ship.next().addClass('selected_ship');
            cursor_pointer();

            if ($(".placed_ship").length == 10) {
                stage = 2;
                $(".header").html('Фаза 2: Морской бой');
                if (lang == 'en') $(".header").html('Step 2: Sea battle!');
                $("#ship_list").slideUp(300);
            }
        }
    });

    // Размещение кораблей компьютера
    // Полностью случайный алгоритм
    var iteration = 0;
    while (iteration != 10) {
        var rand = Math.ceil(Math.random() * 100) - 1;
        var rotation = Math.ceil(Math.random() * 2) - 1;
        if (rotation == 0) rotation = 10;

        var index_array_enemy = [rand];
        if (iteration == 0) { // 1x четырехпалубник
            index_array_enemy.push([rand - 1*rotation]);
            index_array_enemy.push([rand - 2*rotation]);
            index_array_enemy.push([rand + 1*rotation]);
        }
        if (iteration == 1 || iteration == 2) { // 2x трехпалубника
            index_array_enemy.push([rand - 1*rotation]);
            index_array_enemy.push([rand + 1*rotation]);
        }
        if (iteration == 3 || iteration == 4 || iteration == 5) { // 3x двупалубника
            index_array_enemy.push([rand - 1*rotation]);
        }

        var can_place = 1;
        // Проверка возможности размещения
        for (var elem in index_array_enemy) {
            if (rotation == 1) {
                var first_index = index_array_enemy[elem].toString();
                var second_index = rand.toString();
                if (first_index.length == 1) first_index = '0'+first_index;
                if (second_index.length == 1) second_index = '0'+second_index;
                if (first_index.substr(0,1) != second_index.substr(0,1)) {
                    $("#log").html('!');
                    index_array_enemy[elem] = 999;
                }
            }
            if ($(enemy_cell[index_array_enemy[elem]]).children().length) can_place = 0;
            if (!$(enemy_cell[index_array_enemy[elem]]).length) can_place = 0;
        }
        // Если проверка прошла - размещаем корабль
        if (can_place == 1) {
            for (var elem in index_array_enemy) {
                $(enemy_cell[index_array_enemy[elem]]).append('<span class="ship_part"></span>');
            }
            clear_zone();
            iteration++;
        }
    }

    // Разворот корабля
    $('body').keydown(function(event) {
        if (event.keyCode == 82) {
            if (ri == 1) ri = 10;
            else ri = 1;
        }
        cursor_pointer();
    });

    $("#ship_list div div").click(function() {
        if (!$(this).hasClass('placed_ship')) {
            $("#ship_list div div").removeClass('selected_ship');
            $(this).addClass('selected_ship');
        }
    });

    $("#ship_random").click(function() {
        if (stage == 1) {
            stage = 2;
            $(".header").html('Фаза 2: Морской бой');
            if (lang == 'en') $(".header").html('Step 2: Sea battle!');
            $("#ship_list").slideUp(300);
            cell.children().remove();
            cell.css('background','#fff');

            var iteration = 0;
            while (iteration != 10) {
                var rand = Math.ceil(Math.random() * 100) - 1;
                var rotation = Math.ceil(Math.random() * 2) - 1;
                if (rotation == 0) rotation = 10;

                var index_array_player = [rand];
                if (iteration == 0) { // 1x четырехпалубник
                    index_array_player.push([rand - 1*rotation]);
                    index_array_player.push([rand - 2*rotation]);
                    index_array_player.push([rand + 1*rotation]);
                }
                if (iteration == 1 || iteration == 2) { // 2x трехпалубника
                    index_array_player.push([rand - 1*rotation]);
                    index_array_player.push([rand + 1*rotation]);
                }
                if (iteration == 3 || iteration == 4 || iteration == 5) { // 3x двупалубника
                    index_array_player.push([rand - 1*rotation]);
                }

                var can_place = 1;
                // Проверка возможности размещения
                for (var elem in index_array_player) {
                    if (rotation == 1) {
                        var first_index = index_array_player[elem].toString();
                        var second_index = rand.toString();
                        if (first_index.length == 1) first_index = '0'+first_index;
                        if (second_index.length == 1) second_index = '0'+second_index;
                        if (first_index.substr(0,1) != second_index.substr(0,1)) {
                            $("#log").html('!');
                            index_array_player[elem] = 999;
                        }
                    }
                    if ($(cell[index_array_player[elem]]).children().length) can_place = 0;
                    if (!$(cell[index_array_player[elem]]).length) can_place = 0;
                }
                // Если проверка прошла - размещаем корабль
                if (can_place == 1) {
                    for (var elem in index_array_player) {
                        $(cell[index_array_player[elem]]).append('<span class="ship_part ship_part_display"></span>');
                    }
                    clear_zone();
                    iteration++;
                }
            }


        }
    });

    // Ход компьютера
    function computer_run() {
        var direction = Math.ceil(Math.random() * 2) - 1;
        var rand = Math.ceil(Math.random() * 100) - 1;
        while ($(cell[rand]).children().hasClass('shot') || $(cell[rand]).children().children().hasClass('hit')) {
            if (direction == 0) {
                rand++;
                if (rand == 100) rand = 0;
            } else {
                rand--;
                if (rand == -1) rand = 99;
            }
        }

        // Обстрел подбитого корабля
        if ($(".hit_ship").length > 0) {
            var cur_index = $(".hit_ship").parent().index();

            var priority = 'vertical';
            if (   (!$(cell[cur_index + 10]).length || $(cell[cur_index + 10]).children().hasClass("shot"))
                && (!$(cell[cur_index - 10]).length || $(cell[cur_index - 10]).children().hasClass("shot")) ) priority = 'horizontal';

            if ( $(cell[cur_index + 1]).children().hasClass("hit_ship")  || $(cell[cur_index - 1]).children().hasClass("hit_ship")) priority = 'horizontal';
            if ( $(cell[cur_index + 10]).children().hasClass('hit_ship') || $(cell[cur_index -10]).children().hasClass('hit_ship')) priority = 'vertical';

            $(".hit_ship").each(function() {
                cur_index = $(this).parent().index();
                if (priority == 'vertical') {
                    if ($(cell[cur_index + 10]).length && !$(cell[cur_index + 10]).children().children().hasClass('hit') && !$(cell[cur_index + 10]).children().hasClass('shot')) rand = cur_index + 10;
                    if ($(cell[cur_index - 10]).length && !$(cell[cur_index - 10]).children().children().hasClass('hit') && !$(cell[cur_index - 10]).children().hasClass('shot')) rand = cur_index - 10;
                }
                if (priority == 'horizontal') {
                    if ($(cell[cur_index + 1]).length && !$(cell[cur_index + 1]).children().children().hasClass('hit') && !$(cell[cur_index + 1]).children().hasClass('shot')) rand = cur_index + 1;
                    if ($(cell[cur_index - 1]).length && !$(cell[cur_index - 1]).children().children().hasClass('hit') && !$(cell[cur_index - 1]).children().hasClass('shot')) rand = cur_index - 1;
                }
            });
        }

        // Выстрел
        if(!$(cell[rand]).children().hasClass('shot') && !$(cell[rand]).children().children().hasClass('hit')) {
            if ($(cell[rand]).children().hasClass('ship_part')) {
                $(cell[rand]).children().addClass('hit_ship').append('<span class="hit"></span>');
                $(cell[rand]).children().append('<span class="explode_hit"></span>');
                check_destroy(rand, 'player_field');
                check_victory();
                hit_list.splice(hit_list.indexOf(rand), 1);
            } else {
                $(cell[rand]).append('<span class="shot"></span>');
                $(cell[rand]).append('<span class="clear_hit"></span>');
                hit_list.splice(hit_list.indexOf(rand), 1);
                run = 1;
            }
        } else hit_list.splice(hit_list.indexOf(rand), 1);

        $(".clear_hit").hide( "explode", {pieces: 8 }, 200).remove();
        $(".explode_hit").hide( "explode", {pieces: 8 }, 200).remove();
        // повторный вызов функции
        if (run == 2 && stage == 2) setTimeout(computer_run, 400);
    }

    // Проверка на полное уничтожение корабля
    function check_destroy(index, field) {
        var curr_cell = cell;
        if (field == 'enemy_field') curr_cell = enemy_cell;
        var ship = [index];

        // Проверка корабля по вертикали
        if ($(curr_cell[index + 10]).children().hasClass('ship_part')) {
            ship.push(index + 10);
            if ($(curr_cell[index + 20]).children().hasClass('ship_part')) {
                ship.push(index + 20);
                if ($(curr_cell[index + 30]).children().hasClass('ship_part')) {
                    ship.push(index + 30);
                }
            }
        }
        if ($(curr_cell[index - 10]).children().hasClass('ship_part')) {
            ship.push(index - 10);
            if ($(curr_cell[index - 20]).children().hasClass('ship_part')) {
                ship.push(index - 20);
                if ($(curr_cell[index - 30]).children().hasClass('ship_part')) {
                    ship.push(index - 30);
                }
            }
        }

        // Проверка корабля по горизонтали - и это ппц!
        var hpos = index.toString();
        if (hpos.length == 1) hpos = '0'+hpos;
        if ($(curr_cell[index - 1]).children().hasClass('ship_part')) {
            var hpos_now = (index - 1).toString();
            if (hpos_now.length == 1) hpos_now = '0'+hpos_now;
            if (hpos_now.substr(0,1) == hpos.substr(0,1)) {
                ship.push(index - 1);
                if ($(curr_cell[index - 2]).children().hasClass('ship_part')) {
                    var hpos_now = (index - 2).toString();
                    if (hpos_now.length == 1) hpos_now = '0'+hpos_now;
                    if (hpos_now.substr(0,1) == hpos.substr(0,1)) {
                        ship.push(index - 2);
                        if ($(curr_cell[index - 3]).children().hasClass('ship_part')) {
                            var hpos_now = (index - 3).toString();
                            if (hpos_now.length == 1) hpos_now = '0'+hpos_now;
                            if (hpos_now.substr(0,1) == hpos.substr(0,1)) {
                                ship.push(index - 3);
                            }
                        }
                    }
                }
            }
        }
        if ($(curr_cell[index + 1]).children().hasClass('ship_part')) {
            var hpos_now = (index + 1).toString();
            if (hpos_now.length == 1) hpos_now = '0'+hpos_now;
            if (hpos_now.substr(0,1) == hpos.substr(0,1)) {
                ship.push(index + 1);
                if ($(curr_cell[index + 2]).children().hasClass('ship_part')) {
                    var hpos_now = (index + 2).toString();
                    if (hpos_now.length == 1) hpos_now = '0'+hpos_now;
                    if (hpos_now.substr(0,1) == hpos.substr(0,1)) {
                        ship.push(index + 2);
                        if ($(curr_cell[index + 3]).children().hasClass('ship_part')) {
                            var hpos_now = (index + 3).toString();
                            if (hpos_now.length == 1) hpos_now = '0'+hpos_now;
                            if (hpos_now.substr(0,1) == hpos.substr(0,1)) {
                                ship.push(index + 3);
                            }
                        }
                    }
                }
            }
        }

        // Проверка на уничтоженность корабля
        var destroy = 1;
        for (var element in ship ) {
            if(!$(curr_cell[ship[element]]).children().children().hasClass('hit')) destroy = 0;
        }
        // Уничтожаем корабль, если он действительно уничтожен
        if (destroy == 1) {
            var clear_index_array = [];
            for (var element in ship) {
                $(curr_cell[ship[element]]).children().removeClass('hit_ship').addClass('destroyed_ship').append('<span class="explode_hit"></span>');
                if (curr_cell.parent().attr('id') == 'field_1') hit_list.splice(hit_list.indexOf(curr_cell.index()), 1);
                var cur_index = ship[element];
                var additional_indexer = cur_index.toString();
                if (cur_index < 10) additional_indexer = '0' + additional_indexer;
                if (cur_index.toString().substr(1) != 9 && cur_index > 9)   clear_index_array.push([cur_index - 9]);
                if (additional_indexer.substr(1) != 9)                    clear_index_array.push([cur_index + 1]);
                if (additional_indexer.substr(1) != 9 && cur_index < 90) clear_index_array.push([cur_index + 11]);
                if (cur_index < 90)                                        clear_index_array.push([cur_index + 10]);
                if (cur_index < 90 && additional_indexer.substr(1) != 0)  clear_index_array.push([cur_index + 9]);
                if (additional_indexer.substr(1) != 0)                    clear_index_array.push([cur_index - 1]);
                if (cur_index.toString().substr(1) != 0 && cur_index > 9)  clear_index_array.push([cur_index - 11]);
                if (cur_index > 9)                                         clear_index_array.push([cur_index - 10]);
            }
            for (var element in clear_index_array) {
                if (!$(curr_cell[clear_index_array[element]]).children().hasClass('ship_part') &&
                    !$(curr_cell[clear_index_array[element]]).children().hasClass('shot')) $(curr_cell[clear_index_array[element]]).append('<span class="shot"></span>');

            }
        }
    }

    // Проверка победы
    function check_victory() {
        var hit = enemy_cell.find('.hit').length;
        var enemy_hit = cell.find('.hit').length;
        if (hit == 20 || enemy_hit == 20) {
            stage = 3;
            $("#finish_game").show();
            if (hit == 20) {
                $("#who_win").html('Вы победили!');
                if (lang == 'en') $("#who_win").html('You win!');
            }
            if (enemy_hit == 20) {
                $("#who_win").html('Вы проиграли =(');
                if (lang == 'en') $("#who_win").html('You lose =(');
                $("#finish_game").css('box-shadow','0 0 10px red');
            }
            $("#your_score").html(hit);
            $("#computer_score").html(enemy_hit);
        }
    }

    // зона отчуждения
    function clear_zone() {
        // Размещяем зону отчуждения
        var clear_index_array = [];
        $("#field_1").find('.ship_part').parent().each(function() {
            var cur_index = $(this).index();
            var additional_indexer = cur_index.toString();
            if (cur_index < 10) additional_indexer = '0' + additional_indexer;
            if (cur_index.toString().substr(1) != 9 && cur_index > 9)   clear_index_array.push([cur_index - 9]);
            if (additional_indexer.substr(1) != 9)                    clear_index_array.push([cur_index + 1]);
            if (additional_indexer.substr(1) != 9 && cur_index < 90) clear_index_array.push([cur_index + 11]);
            if (cur_index < 90)                                        clear_index_array.push([cur_index + 10]);
            if (cur_index < 90 && additional_indexer.substr(1) != 0)  clear_index_array.push([cur_index + 9]);
            if (additional_indexer.substr(1) != 0)                    clear_index_array.push([cur_index - 1]);
            if (cur_index.toString().substr(1) != 0 && cur_index > 9)  clear_index_array.push([cur_index - 11]);
            if (cur_index > 9)                                         clear_index_array.push([cur_index - 10]);
        });
        for (var element in clear_index_array) {
            if (!$(cell[clear_index_array[element]]).children().length) $(cell[clear_index_array[element]]).append('<span class="clear_zone"></span>');
        }

        clear_index_array = [];
        $("#field_enemy").find('.ship_part').parent().each(function() {
            var cur_index = $(this).index();
            var additional_indexer = cur_index.toString();
            if (cur_index < 10) additional_indexer = '0' + additional_indexer;
            if (cur_index.toString().substr(1) != 9 && cur_index > 9)   clear_index_array.push([cur_index - 9]);
            if (additional_indexer.substr(1) != 9)                    clear_index_array.push([cur_index + 1]);
            if (additional_indexer.substr(1) != 9 && cur_index < 90) clear_index_array.push([cur_index + 11]);
            if (cur_index < 90)                                        clear_index_array.push([cur_index + 10]);
            if (cur_index < 90 && additional_indexer.substr(1) != 0)  clear_index_array.push([cur_index + 9]);
            if (additional_indexer.substr(1) != 0)                    clear_index_array.push([cur_index - 1]);
            if (cur_index.toString().substr(1) != 0 && cur_index > 9)  clear_index_array.push([cur_index - 11]);
            if (cur_index > 9)                                         clear_index_array.push([cur_index - 10]);
        });
        for (var element in clear_index_array) {
            if (!$(enemy_cell[clear_index_array[element]]).children().length) $(enemy_cell[clear_index_array[element]]).append('<span class="clear_zone"></span>');
        }
    }



    function cursor_pointer() {
        cell.css('background', '#fff');
        var cur_index = $(".hover").index();
        index_array = [cur_index];
        if ($(".selected_ship").hasClass('p_4')){
            index_array.push([cur_index - 1*ri]);
            index_array.push([cur_index - 2*ri]);
            index_array.push([cur_index + 1*ri]);
        }
        if ($(".selected_ship").hasClass('p_3')){
            index_array.push([cur_index - 1*ri]);
            index_array.push([cur_index + 1*ri]);
        }
        if ($(".selected_ship").hasClass('p_2')){
            index_array.push([cur_index - 1*ri]);
        }

        // Проверяем на возможность размещения
        possible = true;
        for (var element in index_array ) {
            // Для горизонтального размещения
            if (ri == 1) {
                var first_index = index_array[element].toString();
                var second_index = cur_index.toString();
                if (first_index.length == 1) first_index = '0'+first_index;
                if (second_index.length == 1) second_index = '0'+second_index;
                if (first_index.substr(0,1) != second_index.substr(0,1)) {
                    $("#log").html('!');
                    index_array[element] = 999;
                }
            }
            if (!$(cell[index_array[element]]).length) possible = false;
            if ($(cell[index_array[element]]).children().length) possible = false;

        }

        for (var element in index_array ) {
            if (possible == true) {
                $(cell[index_array[element]]).css('background', '#dbffd9');
            }
            if (possible == false) {
                $(cell[index_array[element]]).css('background', '#f0e0d9');
            }
        }
    }

    function get_symbol(index) {
        index = (index / 10).toString().substr(2);
        if (lang == 'en') {
            if (index == '')  index = 'A';
            if (index == '1') index = 'B';
            if (index == '2') index = 'C';
            if (index == '3') index = 'D';
            if (index == '4') index = 'E';
            if (index == '5') index = 'F';
            if (index == '6') index = 'G';
            if (index == '7') index = 'H';
            if (index == '8') index = 'I';
            if (index == '9') index = 'J';
        } else {
            if (index == '')  index = 'А';
            if (index == '1') index = 'Б';
            if (index == '2') index = 'В';
            if (index == '3') index = 'Г';
            if (index == '4') index = 'Д';
            if (index == '5') index = 'Е';
            if (index == '6') index = 'Ж';
            if (index == '7') index = 'З';
            if (index == '8') index = 'И';
            if (index == '9') index = 'К';
        }
        return(index);
    }

    function get_num(index) {
        index = (((index) / 10) | 0) + 1;
        return(index);
    }
    /**/
});
