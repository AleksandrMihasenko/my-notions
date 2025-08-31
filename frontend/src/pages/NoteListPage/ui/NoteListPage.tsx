import { useTranslation } from 'react-i18next';

const NoteListPage = () => {
	const { t } = useTranslation('translation');

	return <div>{ t('notesPage') }</div>
}

export default NoteListPage;
