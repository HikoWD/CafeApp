using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Table;

namespace Cafe.Service.Models.Operator
{
    public class OperatorProvider
    {
        private OperatorRepository _operatorRepository;

        public OperatorProvider(OperatorRepository operatorRepository)
        {
            _operatorRepository = operatorRepository;
        }

        public CollectionResponse<Operator> Get()
        {
            return new CollectionResponse<Operator>(_operatorRepository.Get());
        }

        public Operator Get(int id)
        {
            return _operatorRepository.Get().FirstOrDefault(@operator => @operator.Id == id);
        }

        public Operator Add(Operator @operator)
        {
            return _operatorRepository.Add(@operator);
        }

        public Operator Edit(Operator @operator)
        {
            return _operatorRepository.Edit(@operator);
        }

        public Operator Remove(Operator @operator)
        {
            return _operatorRepository.Remove(@operator);
        }
    }
}
