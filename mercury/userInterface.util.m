/**
 * Provides utility predicates and functions to construct a user interface.

 * @author Pedro Mariano
 * @version 1.0 2014/02/18
 */
:- module userInterface.util.

:- interface.

:- func selectOneOfField(get(D, F), set(D, F), func(F) = maybe(int), func(F, int) = setResult(F), list(choiceItem(F))) = dialogAction(D).

/**
 * Pack an user interface by pushing lists with a single dialog item to the parent list.
 */
:- func pack(userInterface(D)) = userInterface(D).

:- implementation.

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

pack(UI) = UI.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Implementation of private predicates and functions

:- end_module userInterface.util.

%%% Local Variables: 
%%% mode: mercury
%%% mode: flyspell-prog
%%% ispell-local-dictionary: "british"
%%% End:
