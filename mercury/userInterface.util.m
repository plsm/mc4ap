/**
 * Provides utility predicates and functions to construct a user interface.

 * @author Pedro Mariano
 * @version 1.0 2014/02/18
 */
:- module userInterface.util.

:- interface.

:- func selectOneOfField(
	userInterface.get(D, F),
	userInterface.set(D, F),
	func(F) = maybe(int),
	func(F, int) = userInterface.setResult(F),
	list(userInterface.choiceItem(F))
	) = userInterface.dialogAction(D).

%% ************************************************************************
%% makeSelectOneOf(GetFunc, SetFunc, ListInterfaceData, ListFieldValues)
%%
%% Create a set of radio buttons from a list of values of some field data.
%% Parameters {@code GetFunc} and {@code SetFunc} are functions to get and
%% set the field data.  Parameter {@code ListFieldValues} contains the list
%% of values.  Parameter {@code ListInterfaceData} is used to construct the
%% radio buttons labels.  This parameter must have the same length as list
%% {@code ListFieldValues} otherwise an exception is thrown.
%%
:- func makeSelectOneOf(
	userInterface.get(D, F),
	userInterface.set(D, F),
	list(userInterface.interfaceData),
	list(F)
	) = userInterface.dialogAction(D).

/**
 * Pack an user interface by pushing lists with a single dialog item to the parent list.
 */
:- func pack(userInterface.userInterface(D)) = userInterface.userInterface(D).

:- implementation.

:- import_module exception.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Definition of exported types

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Definition of private types

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Implementation of exported predicates and functions

selectOneOfField(GetField, SetField, SelectedChoice, SelectChoice, ListChoiceItems) = Result :-
	Result = 'new selectOneOf'(SelectedFieldChoice, SelectFieldChoice, SetField, ListChoiceItems),
	SelectedFieldChoice =
	(func(Data) = R :-
		GetField(Data) = Field,
		SelectedChoice(Field) = MChoice,
		(
			MChoice = no,
			R = no
			;
			MChoice = yes(Index),
			R = yes(cc(Index, Field))
		)
	),
	SelectFieldChoice =
	(func(Data, Index) = R :-
		GetField(Data) = Field,
		SelectChoice(Field, Index) = SetResult,
		(
			SetResult = ok(NewField),
			R = ok(sc(Data, NewField))
			;
			SetResult = error(Error),
			R = error(Error)
		)
	).

makeSelectOneOf(GetFunc, SetFunc, ListInterfaceData, ListFieldValues) = Result :-
	SelectedChoice =
	(func(Data) = R :-
		Value = GetFunc(Data),
		(if
			list.nth_member_search(ListFieldValues, Value, Position)
		then
			R = yes(Position)
		else
			R = no
		)
	),
	SetChoice =
	(func(OldData, Index) = R :-
		(if
			Index < list.length(ListFieldValues)
		then
			Value = list.det_index0(ListFieldValues, Index),
			R = SetFunc(OldData, Value)
		else
			throw("userInterface.util.makeSelectOneOf/4: should not be called")
		)
	),
	MakeChoiceItem =
	(func(InterfaceData, _Value) = R :-
		R = ci(InterfaceData, [])
	),
	ListChoiceItem = list.map_corresponding(MakeChoiceItem, ListInterfaceData, ListFieldValues),
	Dialog = selectOneOf(SelectedChoice, SetChoice, ListChoiceItem),
	Result = Dialog
	.

pack(UI) = UI.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Implementation of private predicates and functions

:- func selectedChoice(userInterface.get(D, F), list(F), D) = maybe(int).

selectedChoice(GetFunc, ListFieldValues, Data) = Result :-
	Value = apply(GetFunc, Data),
	(if
		list.nth_member_search(ListFieldValues, Value, Position)
	then
		Result = yes(Position)
	else
		Result = no
	)
	.

:- func setChoice(userInterface.set(D, F), list(F), D, int) = userInterface.setResult(D).

setChoice(SetFunc, ListFieldValues, OldData, Index) = Result :-
	(if
		Index < list.length(ListFieldValues)
	then
		Value = list.det_index0(ListFieldValues, Index),
		NewData = SetFunc(OldData, Value),
		Result = NewData
	else
		throw("userInterface.util.setChoice/4: should not be called")
	)
	.

:- func makeChoiceItem(interfaceData, F) = choiceItem(D).

makeChoiceItem(InterfaceData, _Value) = ci(InterfaceData, []).
	
:- end_module userInterface.util.

%%% Local Variables: 
%%% mode: mercury
%%% mode: flyspell-prog
%%% ispell-local-dictionary: "british"
%%% End:
