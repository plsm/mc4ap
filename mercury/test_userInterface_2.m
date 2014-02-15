/**
 * 

 * @author Pedro Mariano
 * @version 1.0 2013/09/14
 */
:- module test_userInterface_2.

:- interface.

:- import_module io.

:- pred main(io.state, io.state).
:- mode main(di, uo) is det.

:- implementation.

:- import_module ui_console, ui_swing, userInterface.
%:- import_module my, my.random.
:- import_module bool, exception, float, int, list, maybe, string.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Definition of exported types

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Definition of private types

:- type point --->
	point(
		weight :: float,
		label :: string,
		x :: int,
		y :: int
	).

:- type linha --->
	line(
		from  :: point,
		to    :: point,
		name  :: string
	).

:- type figure --->
	circle ;
	square ;
	point(
		figurePoint :: point).

:- inst figurePoint == bound(point(ground)).

:- type polygon --->
	polygon(
		points :: list(point),
		namePol :: string
	).

:- type data --->
	data(
%		random               :: my.random.supplyParameter,
		linha    :: linha,
		polygon1 :: polygon,
		polygon2 :: polygon,
		polygons :: list(polygon),
		words    :: list(string),
		ints     :: list(int),
		linhas   :: list(linha),
		figure   :: figure
	).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Implementation of exported predicates and functions

main(!IO) :-

	readData(InitialData, !IO),
	
	ui_console.show(mainMenu, InitialData, Result1, !IO),
	io.print(Result1, !IO),
	io.nl(!IO),
	writeData(Result1, !IO),

	
	ui_swing.show("Test user_interface module", mainMenu, InitialData, Result2, !IO),
	io.print(Result2, !IO),
	io.nl(!IO),

	io.print(if Result1 = Result2 then yes else no, !IO),
	io.nl(!IO),

	writeData(Result2, !IO),
	true.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Implementation of private predicates and functions

:- pred readData(data, io.state, io.state).
:- mode readData(out, di, uo) is det.

readData(Data, !IO) :-
	io.open_input("data.txt", IStream, !IO),
	(
		IStream = ok(Stream),
		io.read(Stream, IData, !IO),
		(
			IData = ok(Data)
			;
			IData = eof,
			Data = initialData
			;
			IData = error(Message, Line),
			io.format("%d: %s", [i(Line), s(Message)], !IO),
			Data = initialData
		),
		io.close_input(Stream, !IO)
		;
		IStream = error(Error),
		io.format("IO error opening `data.txt` file: %s", [s(io.error_message(Error))], !IO),
		Data = initialData
	).

:- pred writeData(data, io.state, io.state).
:- mode writeData(in, di, uo) is det.

writeData(Data, !IO) :-
	io.open_output("data.txt", IStream, !IO),
	(
		IStream = ok(Stream),
		io.write(Stream, Data, !IO),
		io.print(Stream, ".\n", !IO),
		io.close_output(Stream, !IO)
		;
		IStream = error(Error),
		io.format("IO error opening `data.txt` file: %s", [s(io.error_message(Error))], !IO)
	).

:- func initialData = data.

initialData = InitialData :-
	InitialLinha = line(point(1.5, "bla", 2, 3), point(2.5, "foo", 5, 5), "linha"),
	InitialPolygon = polygon([point(1.5, "aaa", 2, 3), point(4.0, "bbb", 5, 6)], "vazio"),
%	InitialData^random = mt(clock),
	InitialData = data(
		InitialLinha,
		InitialPolygon,
		polygon([point(7.77, "ccc", 8, 9) | InitialPolygon^points], "três"),
		[],
		["test", "user", "interface"],
		[],
		[],
		point(point(1.5, "aaa", 2, 3))
	)
	% InitialData^linha = InitialLinha,
	% InitialData^polygon1 = InitialPolygon,
	% InitialData^polygon2 = polygon([point(7.77, "ccc", 8, 9) | InitialPolygon^points], "três"),
	% InitialData^polygons = [],
	% InitialData^words = ["test", "user", "interface"],
	% InitialData^ints = [],
	% InitialData^linhas = [],
	% InitialData^figure = point(point(1.5, "aaa", 2, 3))
	.

:- func initialPolygon = polygon.

initialPolygon = polygon([point(1.5, "aaa", 2, 3), point(4.0, "bbb", 5, 6)], "vazio").

:- func mainMenu = userInterface(data).
:- mode mainMenu = out(userInterface).

mainMenu = Result :-
	DialogPoint =
	[
	 di(label("weight"), updateFieldFloat(  weight, setWeight)),
	 di(label("label"),  updateFieldString( label,  setLabel)),
	 di(label("x"),      updateFieldInt(    x,      setX)),
	 di(label("y"),      updateFieldInt(    y,      setY)),
	 di(label("zero"),   newValue(point(1.0, "zero", 0, 0)))
	],
	DialogLine =
	[
	 di(label("name"),  updateFieldString(name, setName)),
	 di(label("from"),  'new editField'(from,   setFrom, DialogPoint)),
	 di(label("to"),    'new editField'(to,     setTo, DialogPoint))
	],
	DialogPolygon =
	[
	 di(label("points"), 'new editListFieldAny'(points, setPoints, point(1.0, "", 0, 0), DialogPoint)),
	 di(label("name"),   updateFieldString(namePol, setNamePol))
	],
	DialogLinhas =
	[
	 di(label("line list"), 'new editListFieldAny'(get_linhas, set_linhas, line(point(1.0, "A", 0, 0), point(2.0, "B", 1, 1), "AB"), DialogLine))
	],
	DialogLists =
	[
	 di(label("nothing 1")),
	 di(label("words"),     updateListFieldString( getWords, setWords )),
	 di(label("nothing 2")),
	 di(label("ints"),      updateListFieldInt( getInts, setInts ))
	],
	ActionItemFigure = 'new selectOneOf'(
		getCurrentChoice,
	 	setChoice,
		setData,
		[
			ci(label("circle"), []),
			ci(label("square"), []),
			ci(label("point"),  [di(label("point"), 'new editField'(figurePoint, set('figurePoint :='), DialogPoint))])
		]),
	MainMenu =
	[
	 mi(label("line"),    submenu(LineMenu)),
	 mi(label("line list"),    edit(dialog(DialogLinhas))),
%	 mi(label("figure"),    edit('new dialog'(get_figure, set_figure, [DialogItemFigure]))),
	 mi(label("figure"),    edit(dialog([di(label("figure"), ActionItemFigure)]))),
	 mi(label("polygon"),  submenu(PolygonMenu)),
	 mi(label("polygons"), edit(dialog([di(label("polygon"),
		'new editListFieldAny'(polygons, set('polygons :='), initialPolygon, DialogPolygon) )]))),
	 mi(label("lists"),   edit(dialog(DialogLists))),
	 mi(label("help"),    submenu(HelpMenu))
	],
	LineMenu =
	[
	 mi(label("new"),   updateData(  resetLine)),
	 mi(label("save"),  updateDataIO( saveLine)),
	 mi(label("edit"),  edit('new dialog'(getLinha,   setLinha,  DialogLine)))
	],
	PolygonMenu =
	[
	 mi(label("edit 1"),   edit('new dialog'(getPolygon(1), setPolygon(1), DialogPolygon))),
	 mi(label("edit 2"),   edit('new dialog'(getPolygon(2), setPolygon(2), DialogPolygon)))
	 ],
	HelpMenu =
	[
	 mi(label("index"),  actionIO(help))
	 ],
	Result = m(MainMenu).





:- func getCurrentChoice(data) = maybe(currentChoice(figure)).

getCurrentChoice(Data) = Result :-
	Data^figure = circle,
	Result = yes(cc(0, Data^figure))
	;
	Data^figure = square,
	Result = yes(cc(1, Data^figure))
	;
	Data^figure = point(_),
	Result = yes(cc(2, Data^figure))
	.

:- func setChoice(data, int) = setResult(selectChoice(data, figure)).

setChoice(Data, Index) = ok(SC) :-
	(if
		Index = 0,
		Data^figure = circle,
		NextFigure = Data^figure
		;
		Index = 0,
		Data^figure = square,
		NextFigure = circle
		;
		Index = 0,
		Data^figure = point(_),
		NextFigure = circle
		;
		Index = 1,
		Data^figure = circle,
		NextFigure = square
		;
		Index = 1,
		Data^figure = square,
		NextFigure = Data^figure
		;
		Index = 1,
		Data^figure = point(_),
		NextFigure = square
		;
		Index = 2,
		Data^figure = circle,
		NextFigure = point(point(1.0, "new", 0, 0))
		;
		Index = 2,
		Data^figure = square,
		NextFigure = point(point(1.0, "new", 0, 0))
		;
		Index = 2,
		Data^figure = point(_),
		NextFigure = Data^figure
	then
		NextData =
		(if
			NextFigure = Data^figure
		then
			Data
		else
			'figure :='(Data, NextFigure)
		),
		SC = sc(NextData, NextFigure)
	else
		throw("Invalid index")
	).

% setChoice(Data, Index) = ok(SC) :-
% 	(if
% 		Index = 0
% 	then
% 		Data^figure = circle,
% 		SC = sc(Data, Data^figure)
% 		;
% 		Data^figure = square,
% 		SC = sc('figure :='(Data, circle), circle)
% 		;
% 		Data^figure = point(_),
% 		SC = sc('figure :='(Data, circle), circle)
% 	else if
% 		Index = 1
% 	then
% 		Data^figure = circle,
% 		SC = sc('figure :='(Data, square), square)
% 		;
% 		Data^figure = square,
% 		SC = sc(Data, Data^figure)
% 		;
% 		Data^figure = point(_),
% 		SC = sc('figure :='(Data, square), square)
% 	else if
% 		Index = 2
% 	then
% 		Data^figure = circle,
% 		SC = sc(Data, point(point(1.0, "new", 0, 0)))
% 		;
% 		Data^figure = square,
% 		SC = sc(Data, point(point(1.0, "new", 0, 0)))
% 		;
% 		Data^figure = point(_),
% 		SC = sc(Data, Data^figure)
% 	else
% 		throw("Invalid index")
% 	).

:- func setData(data, figure) = setResult(data).

setData(Data, Figure) = ok('figure :='(Data, Figure)).



:- func resetLine(data) = data.

resetLine(Data) = 'linha :='(Data, line(point(1.5, "bla", 2, 3), point(2.5, "foo", 5, 5), "linha")).

:- pred saveLine(data, data, io.state, io.state).
:- mode saveLine(in, out, di, uo) is det.

saveLine(!Data, !IO) :-
	io.print(!.Data^linha, !IO),
	io.nl(!IO).

:- pred help(io.state, io.state).
:- mode help(di, uo) is det.

help(!IO) :-
	io.print("Help text for test_userInterface_2\n", !IO).

% getters and setters figure

:- func figurePoint(figure) = point.
:- mode figurePoint(in(figurePoint)) = out is det.
:- func 'figurePoint :='(figure, point) = figure.
:- mode 'figurePoint :='(in(figurePoint), in) = out is det.

% getters and setters data

:- func polygons(data) = list(polygon).
:- func 'polygons :='(data, list(polygon)) = data.

:- func getPolygon(int, data) = polygon.

getPolygon(N, Data) =
	(if
		N = 1
	then
		Data^polygon1
	else
		Data^polygon2
	).

:- func setPolygon(int, data, polygon) = setResult(data).

setPolygon(N, Data, Polygon) =
	(if
		N = 1
	then
		ok('polygon1 :='(Data, Polygon))
	else
		ok('polygon2 :='(Data, Polygon))
	).

:- func getLinha(data) = linha.

getLinha(Data) = Data^linha.

:- func setLinha(data, linha) = setResult(data).

setLinha(Data, Linha) = ok('linha :='(Data, Linha)).



:- func get_linhas(data) = list(linha).

get_linhas(Data) = Data^linhas.

:- func set_linhas(data, list(linha)) = setResult(data).

set_linhas(Data, Linhas) = ok('linhas :='(Data, Linhas)).



:- func getWords(data) = list(string).

getWords(D) = D^words.

:- func setWords(data, list(string)) = setResult(data).

setWords(D, L) =
	(if
		list.member("hello", L),
		list.member("world", L)
	then
		error("No hello world is permitted")
	else
		ok('words :='(D, L))
	).

:- func getInts(data) = list(int).

getInts(D) = D^ints.

:- func setInts(data, list(int)) = setResult(data).

setInts(D, L) =
	(if
		list.member(666, L),
		list.member(-1, L)
	then
		error("The beast!!!")
	else
		ok('ints :='(D, L))
	).


:- func get_figure(data) = figure.

get_figure(D) = D^figure.

:- func set_figure(data, figure) = setResult(data).

set_figure(D, L) = ok('figure :='(D, L)).


% getters and setters polygon

:- func namePol(polygon) = string.

:- func setNamePol(polygon, string) = setResult(polygon).

setNamePol(Pol, Name) = ok('namePol :='(Pol, Name)).

:- func points(polygon) = list(point).

:- func setPoints(polygon, list(point)) = setResult(polygon).

setPoints(Polygon, Points) = Result :-
	(if
		list.append(_, [P | Rest], Points),
		list.member(P, Rest)
	then
		Result = error("Repeated points are not allowed")
	else
		Result = ok('points :='(Polygon, Points))
	).
				 

% getters and setters linha

:- func from(linha) = point.

:- func setFrom(linha, point) = setResult(linha).

setFrom(Line, Point) = ok('from :='(Line, Point)).

:- func to(linha) = point.

:- func setTo(linha, point) = setResult(linha).

setTo(Line, Point) = ok('to :='(Line, Point)).

:- func name(linha) = string.

:- func setName(linha, string) = setResult(linha).

setName(Line, Name) = ok('name :='(Line, Name)).

% getters and setters point

:- func x(point) = int.

:- func y(point) = int.

:- func setX(point, int) = setResult(point).

setX(Point, X) = ok('x :='(Point, X)).

:- func setY(point, int) = setResult(point).

setY(Point, Y) = ok('y :='(Point, Y)).

:- func weight(point) = float.

:- func setWeight(point, float) = setResult(point).

setWeight(Point, Weight) =
	(if
		Weight > 0.0
	then
		ok('weight :='(Point, Weight))
	else
		error("weight must be positive")
	).

:- func label(point) = string.

:- func setLabel(point, string) = setResult(point).

setLabel(Point, Label) = ok('label :='(Point, Label)).


:- end_module test_userInterface_2.

%%% Local Variables: 
%%% mode: mercury
%%% mode: flyspell-prog
%%% ispell-local-dictionary: "british"
%%% End:
