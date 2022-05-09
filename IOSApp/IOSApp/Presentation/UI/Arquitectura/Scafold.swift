import SwiftUI
import sharedApp

struct Scafold: View {

    private let caseUses: UseCases
    
    //Obtengo los casos de uso.
    init(
        caseUses: UseCases
    ){
        self.caseUses = caseUses
    }
    
	var body: some View {
		Despensa(caseUses: caseUses)
	}
}

