import SwiftUI
import sharedApp

@main
struct iOSApp: App {
    
    private let cacheModule: CacheModule
    private let caseUses: UseCases
    
    //Inicializo la base de datos y los casos de uso que se utilizarán en toda la APP.
    init(){
        self.cacheModule = CacheModule()
        self.caseUses = UseCases(cacheModule: self.cacheModule)
    }
    
	var body: some Scene {
		WindowGroup {
			Scafold(
                caseUses: caseUses
            )
		}
	}
}
